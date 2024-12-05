package com.rahmat.basicauth.model.jwt;

import lombok.Data;

@Data
public class ValidateResponse {
	private String statusCode;
	private String statusMessage;
	private Auth authData;
}
