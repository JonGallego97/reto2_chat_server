package com.example.reto2_chat_server.chat.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reto2_chat_server.chat.service.ChatService;
import com.example.reto2_chat_server.model.Chat;
import com.example.reto2_chat_server.security.user.repository.UserDAO;
@RestController
@RequestMapping("api")
public class ChatController {
	@Autowired
	ChatService chatService;
	
	@GetMapping("/chats")
	public ResponseEntity<?> chats(Authentication authentication) {
	    try {
	        UserDAO userDetails = (UserDAO) authentication.getPrincipal();
	        List<Chat> response = chatService.getChats(userDetails.getId());
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        // Loguea la excepción para obtener más detalles
	        e.printStackTrace();
	        return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}
