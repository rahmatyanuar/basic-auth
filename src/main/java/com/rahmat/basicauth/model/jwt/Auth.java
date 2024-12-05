package com.rahmat.basicauth.model.jwt;

import lombok.Data;

@Data
public class Auth {
	private String username;
	private String email;
	private Boolean isAuth;
	private Boolean credentialIsNotExpired;
	private Boolean accountIsNotExpired;
	private Boolean accountNotLocked;
	private String sessionId;
	private String remoteAdd;
}
