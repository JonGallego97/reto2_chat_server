package com.example.reto2_chat_server.security.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reto2_chat_server.security.configuration.JwtTokenUtil;
import com.example.reto2_chat_server.security.user.repository.UserDAO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenUtil tokenUtil;
	
	@PostMapping("auth/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
					);
			UserDAO userDAO = (UserDAO) authentication.getPrincipal();
			String accessToken = tokenUtil.generateAccessToken(userDAO);
			AuthResponse response = new AuthResponse(userDAO.getEmail(), accessToken);
			return ResponseEntity.ok().body(response);
		} catch (BadCredentialsException ex) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		
	}
	
	@GetMapping("/auth/me")
	public ResponseEntity<?>getUserInfo(Authentication authentication){
		UserDAO userDetails = (UserDAO) authentication.getPrincipal();
		return ResponseEntity.ok().body(userDetails);
		
	}

}
