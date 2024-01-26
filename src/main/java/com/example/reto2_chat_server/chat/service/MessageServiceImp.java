package com.example.reto2_chat_server.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reto2_chat_server.chat.repository.Chat;
import com.example.reto2_chat_server.chat.repository.MessageRepository;
import com.example.reto2_chat_server.model.message.Message;
import com.example.reto2_chat_server.model.message.MessageSend;
import com.example.reto2_chat_server.security.user.repository.UserDAO;
import com.example.reto2_chat_server.security.user.service.UserServiceModel;
@Service
public class MessageServiceImp implements MessageService{

	@Autowired
	MessageRepository messageRepository;
	
	@Override
	public Message insertMessage(MessageSend message) {
		Message messagesDao = new Message(
				message.getId(),
				message.getDataType(),
				message.getContent(),
				message.getCreatedAt(),
				message.getUpdatedAt(),
				convertUser(message.getUserId()),
				convertChat(message.getChat())
			);
		
		
		
		return messageRepository.save(messagesDao);
	}

	private Chat convertChat(ChatServiceModel chat) {
		// TODO Auto-generated method stub
		return new Chat(chat.getId());
	}

	private UserDAO convertUser(UserServiceModel userId) {
		// TODO Auto-generated method stub
		return new UserDAO(userId.getId());
	}

}
