package com.rahmat.basicauth.model.jwt;

import java.io.Serializable;

import lombok.Data;

@Data
public class SignUpRequest implements Serializable{
	private static final long serialVersionUID = -8091879091924046844L;
	
	private String username;
	private String password;
	private String email;
}
