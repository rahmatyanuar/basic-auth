package com.rahmat.basicauth.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rahmat.basicauth.configuration.jwt.JwtTokenUtil;
import com.rahmat.basicauth.implementation.UserDetailsServiceImpl;
import com.rahmat.basicauth.model.Role;
import com.rahmat.basicauth.model.User;
import com.rahmat.basicauth.model.enumeration.ERole;
import com.rahmat.basicauth.model.general.GeneralResponse;
import com.rahmat.basicauth.model.jwt.JwtRequest;
import com.rahmat.basicauth.model.jwt.JwtResponse;
import com.rahmat.basicauth.model.jwt.MessageResponse;
import com.rahmat.basicauth.repo.RoleRepository;
import com.rahmat.basicauth.repo.UserRepository;

@Service
public class AuthServices{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	public GeneralResponse userRegistration(JwtRequest signUpRequest) throws Exception {
		GeneralResponse res = new GeneralResponse();

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			res.setStatusCode("400");
			res.setStatusMessage("Error: Username is already taken!");
			res.setData(null);
			return res;
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			res.setStatusCode("400");
			res.setStatusMessage("Error: Email is already use!");
			res.setData(null);
			return res;
		}
		
		User user = new User();
		user.setUsername(signUpRequest.getUsername());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		
		return userSignIn(signUpRequest);
	}
	
	public GeneralResponse userSignIn(JwtRequest signUpRequest) throws Exception {
		GeneralResponse res = new GeneralResponse();
		authenticate(signUpRequest.getUsername(), signUpRequest.getPassword());
		final UserDetails userDetails = userDetailsService
		.loadUserByUsername(signUpRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		Date tokenExp = jwtTokenUtil.getExpirationDateFromToken(token);
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		userDetailsService.updateToken(userDetails.getUsername(), token, tokenExp);
		
		res.setStatusCode("200");
		res.setStatusMessage("Success");
		res.setData(new JwtResponse(token, formatter.format(tokenExp)));
		return res;
	}
	
	public GeneralResponse tokenValidate() throws Exception {
		GeneralResponse res = new GeneralResponse();
	
		res.setStatusCode("200");
		res.setStatusMessage("Success");
		res.setData(new MessageResponse("Token Valid"));
		return res;	
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
