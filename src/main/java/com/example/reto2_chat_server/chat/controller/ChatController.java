package com.example.reto2_chat_server.chat.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reto2_chat_server.chat.model.DAO.ChatDAO;
import com.example.reto2_chat_server.chat.model.serviceModel.ChatServiceModel;
import com.example.reto2_chat_server.chat.service.ChatService;
import com.example.reto2_chat_server.security.user.service.UserServiceModel;
@RestController
@RequestMapping("api")
public class ChatController {
	@Autowired
	ChatService chatService;
	
	@GetMapping("/chats")
	public ResponseEntity<?> chats(Authentication authentication) {
	    UserServiceModel userDetails = (UserServiceModel) authentication.getPrincipal();
        List<ChatServiceModel> response = chatService.getChats(userDetails.getId());
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
