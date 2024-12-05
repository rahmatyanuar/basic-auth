package com.rahmat.basicauth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rahmat.basicauth.model.general.GeneralResponse;
import com.rahmat.basicauth.model.jwt.JwtRequest;
import com.rahmat.basicauth.services.AuthServices;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthServices auth;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok", response = GeneralResponse.class),
			@ApiResponse(code = 400, message = "Error", response = GeneralResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = GeneralResponse.class),
			@ApiResponse(code = 500, message = "Bad Request", response = GeneralResponse.class)
	})
	public ResponseEntity<GeneralResponse> registerUser(@Valid @RequestBody JwtRequest signUpRequest) throws Exception {
		GeneralResponse obj = auth.userRegistration(signUpRequest);
		return (ResponseEntity<GeneralResponse>) ResponseEntity.ok(obj);
	}
	
	@RequestMapping(value = "/token", method = RequestMethod.POST, consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok", response = GeneralResponse.class),
			@ApiResponse(code = 400, message = "Error", response = GeneralResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = GeneralResponse.class),
			@ApiResponse(code = 500, message = "Bad Request", response = GeneralResponse.class)
	})
	public ResponseEntity<GeneralResponse> requestToken(@Valid @RequestBody JwtRequest signInRequest) throws Exception {
		GeneralResponse obj = auth.userSignIn(signInRequest);
		return (ResponseEntity<GeneralResponse>) ResponseEntity.ok(obj);
	}
	
	@RequestMapping(value = "/validate", method = RequestMethod.GET, consumes = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok", response = GeneralResponse.class),
			@ApiResponse(code = 400, message = "Error", response = GeneralResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = GeneralResponse.class),
			@ApiResponse(code = 500, message = "Bad Request", response = GeneralResponse.class)
	})
	public ResponseEntity<GeneralResponse> tokenValidate(Authentication authentication) throws Exception {
		GeneralResponse obj = new GeneralResponse();
		obj.setStatusCode("200");
		obj.setStatusMessage("Success");
		obj.setData(authentication);
		return (ResponseEntity<GeneralResponse>) ResponseEntity.ok(obj);
	}
}
