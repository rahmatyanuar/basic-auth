package com.rahmat.basicauth.model.jwt;

import java.io.Serializable;
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;
	
	private final String jwttoken;
	private final String tokenExp;
	
	public JwtResponse(String jwttoken, String tokenExp) {
		this.jwttoken = jwttoken;
		this.tokenExp = tokenExp;
	}
	public String getToken() {
		return this.jwttoken;
	}
	
	public String getTokenExpDate() {
		return this.tokenExp;
	}
}
