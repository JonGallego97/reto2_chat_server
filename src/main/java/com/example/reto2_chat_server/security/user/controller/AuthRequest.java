package com.example.reto2_chat_server.security.user.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


public class AuthRequest {
	@Override
	public String toString() {
		return "AuthRequest [email=" + email + ", password=" + password + "]";
	}

	@NotNull @Email
	private String email;
	
	@NotNull
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
