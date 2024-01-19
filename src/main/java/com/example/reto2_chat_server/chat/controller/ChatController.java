package com.example.reto2_chat_server.chat.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reto2_chat_server.chat.service.ChatService;
import com.example.reto2_chat_server.chat.service.ChatServiceModel;
import com.example.reto2_chat_server.security.configuration.JwtTokenUtil;
import com.example.reto2_chat_server.security.user.service.UserServiceModel;
@RestController
@RequestMapping("api")
public class ChatController {
	@Autowired
	ChatService chatService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenUtil tokenUtil;
	
	@GetMapping("/chats")
	public ResponseEntity<?> chats(Authentication authentication) {
	    UserServiceModel userDetails = (UserServiceModel) authentication.getPrincipal();
        List<ChatServiceModel> response = chatService.getChats(userDetails.getId());
        userDetails.setListChats(response);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	

}
