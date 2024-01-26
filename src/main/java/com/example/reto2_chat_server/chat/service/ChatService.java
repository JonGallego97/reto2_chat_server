package com.example.reto2_chat_server.chat.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.reto2_chat_server.chat.controller.UsersFromChatsPostRequest;
import com.example.reto2_chat_server.chat.repository.Chat;
import com.example.reto2_chat_server.chat.repository.UsersFromChatDAO;


public interface ChatService {

	List<ChatServiceModel> getChats(int id);
	List<Integer> getChatsIdsByUserId(Integer userId);
	ChatServiceModel createChat(Chat chat);
	ResponseEntity<?> deleteChatById(Integer id);
	ResponseEntity<?> addUsersToChat(int chatId, List<UsersFromChatsPostRequest> usersToAdd);
	ResponseEntity<?> removeUsersFromChat(int chatId,List<UsersFromChatsPostRequest> usersToRemove);

	
}
