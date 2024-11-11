package com.incture.employeemanagementsystem.entities;

public class AuthenticationResponse {
	private String jwt;
	private String error;
	
	
	public String getJwt() {
		return jwt;
	}
	public AuthenticationResponse(String jwt, String error) {
		super();
		this.jwt = jwt;
		this.error = error;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
