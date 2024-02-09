package com.example.reto2_chat_server.chat.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.example.reto2_chat_server.chat.controller.UsersFromChatsPostRequest;
import com.example.reto2_chat_server.chat.repository.Chat;


public interface ChatService {

	List<ChatServiceModel> getChats(int id);
	ChatServiceModel getChatsById(int id);
	List<Integer> getChatsIdsByUserId(Integer userId);
	ResponseEntity<?> createChat(Chat chat, int userId);
	ResponseEntity<?> deleteChatById(Integer id, int userId);
	ResponseEntity<?> addUsersToChat(int chatId, List<UsersFromChatsPostRequest> usersToAdd, int userId, boolean create);
	ResponseEntity<?> removeUsersFromChat(int chatId,List<UsersFromChatsPostRequest> usersToRemove, int userId);
	ResponseEntity<?> getUserNotInChat(int chatId);
	ResponseEntity<?> getUserInChat(int chatId, int userId);
	ResponseEntity<?> getPublicChats(int id);

	
}
