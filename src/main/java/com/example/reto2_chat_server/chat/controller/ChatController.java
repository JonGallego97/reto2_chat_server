package com.example.reto2_chat_server.chat.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.reto2_chat_server.chat.repository.Chat;
import com.example.reto2_chat_server.chat.service.ChatService;
import com.example.reto2_chat_server.chat.service.ChatServiceModel;
import com.example.reto2_chat_server.security.configuration.JwtTokenUtil;
import com.example.reto2_chat_server.security.user.service.UserServiceModel;

import jakarta.persistence.EntityNotFoundException;

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
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/chats/{userId}")
	public ResponseEntity<ChatServiceModel> createChat (@RequestBody ChatPostRequest chatPostRequest, @PathVariable("userId") Integer idUser, Authentication authentication){
		Chat chat = new Chat(chatPostRequest.isPublic(), chatPostRequest.getName());
        UserServiceModel userDetails = (UserServiceModel) authentication.getPrincipal();
		
		
        ResponseEntity<?> response = chatService.createChat(chat, userDetails.getId());
        if (response.hasBody() && response.getBody() instanceof ChatServiceModel) {
        	ChatServiceModel chatServiceModel = (ChatServiceModel) response.getBody();
			UsersFromChatsPostRequest creatorUserRequest = new UsersFromChatsPostRequest(idUser, chatServiceModel.getId(), true);
			List<UsersFromChatsPostRequest> listRequest = new ArrayList<UsersFromChatsPostRequest>();
			listRequest.add(creatorUserRequest);

			ResponseEntity<?> addUserResponse = chatService.addUsersToChat(chatServiceModel.getId(), listRequest, userDetails.getId(), false);
			if (addUserResponse.getStatusCode() != HttpStatus.OK) {	        	
				return new ResponseEntity<ChatServiceModel>(HttpStatus.CONFLICT);
			}
		}

		return (ResponseEntity<ChatServiceModel>) response;
	}

	@PostMapping("/chats/{chatId}/add-users")
	public ResponseEntity<?> addUsersToChat(@PathVariable("chatId") int chatId, @RequestBody List<UsersFromChatsPostRequest> usersToAdd, Authentication authentication) {

		try {
			UserServiceModel userDetails = (UserServiceModel) authentication.getPrincipal();
			ResponseEntity<?> addUserResponse = chatService.addUsersToChat(chatId, usersToAdd, userDetails.getId(), true);

			if (addUserResponse.getStatusCode() != HttpStatus.OK) {
				return new ResponseEntity<>(addUserResponse.getBody(), addUserResponse.getStatusCode());
			}

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar usuarios al chat.");
		}
	}

	@PostMapping("/chats/{chatId}/remove-users")
	public ResponseEntity<?> removeUsersFromChat(@PathVariable("chatId") int chatId, @RequestBody List<UsersFromChatsPostRequest> usersToRemove, Authentication authentication) {
	    try {
			UserServiceModel userDetails = (UserServiceModel) authentication.getPrincipal();
	        ResponseEntity<?> removeUserResponse = chatService.removeUsersFromChat(chatId, usersToRemove, userDetails.getId());


			if (removeUserResponse.getStatusCode() != HttpStatus.OK) {
				return new ResponseEntity<>(removeUserResponse.getBody(), removeUserResponse.getStatusCode());
			}

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar usuarios del chat.");
		}
	}

	@DeleteMapping("/chats/{id}")
	public ResponseEntity<?> deleteChatById (@PathVariable("id") Integer idChat, Authentication authentication) {
		try {
			UserServiceModel userDetails = (UserServiceModel) authentication.getPrincipal();
	        System.out.println(userDetails.getId());
			return chatService.deleteChatById(idChat, userDetails.getId());
		} catch (EmptyResultDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Chat no encontrado");

		}

	}

	@GetMapping("/{chatId}/getUserToAdd")
	public ResponseEntity<?> getAllUsersToAddToChat(@PathVariable("chatId") int chatId) {
		try {
			return chatService.getUserNotInChat(chatId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Error");
		}
	}

	@GetMapping("/{chatId}/getUserToDelete")
	public ResponseEntity<?> getAllUsersToDeleteToChat(@PathVariable("chatId") int chatId,
			Authentication authentication) {
		try {
			UserServiceModel userDetails = (UserServiceModel) authentication.getPrincipal();
			return chatService.getUserInChat(chatId, userDetails.getId());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Error");
		}
	}

	@GetMapping("/chats/public")
	public ResponseEntity<?> getAllPublicChat(Authentication authentication) {
		try {
			UserServiceModel userDetails = (UserServiceModel) authentication.getPrincipal();
			return chatService.getPublicChats(userDetails.getId());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Error");
		}
	}

}
