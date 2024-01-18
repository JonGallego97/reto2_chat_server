package com.example.reto2_chat_server.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reto2_chat_server.chat.repository.ChatRepository;
import com.example.reto2_chat_server.chat.repository.UserChatRepository;
import com.example.reto2_chat_server.model.Chat;
import com.example.reto2_chat_server.model.UserChat;
import com.example.reto2_chat_server.model.User_Chat;

@Service
public class ChatServiveImpl implements ChatService{
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UserChatRepository userRepository;
	
	@Override
	public List<Chat> getChats(int id) {
	    List<Chat> response = new ArrayList<>();

	    Optional<UserChat> userChats = userRepository.findById(id);

	    if (userChats.isPresent()) {
	        List<User_Chat> userChatsList = userChats.get().getChats();
	        List<Integer> chatIds = userChatsList.stream()
	                .map(userChat -> userChat.getChat().getId())
	                .collect(Collectors.toList());
	        System.out.println(chatIds);
	        response = (List<Chat>) chatRepository.findAllById(chatIds);
	        System.out.println("ads");
	        System.out.println(response);
	    }

	    return response;
	}

	
	public List<Integer> getChatIds(int id) {
	    List<Integer> response = new ArrayList<>();
	    
	    Optional<UserChat> userChats = userRepository.findById(id);
	    
	    if (userChats.isPresent()) {
	        List<User_Chat> userChatsList = userChats.get().getChats();
	        response = userChatsList.stream()
	                .map(userChat -> userChat.getChat().getId())
	                .collect(Collectors.toList());
	    }
	    
	    return response;
	}
}
