package com.rahmat.basicauth.model.jwt;

import java.io.Serializable;

import lombok.Data;

@Data
public class SignInRequest implements Serializable{
	private static final long serialVersionUID = -8091879091924046844L;
	
	private String username;
	private String password;
}
