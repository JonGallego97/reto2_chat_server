package com.example.reto2_chat_server.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reto2_chat_server.chat.model.DAO.ChatDAO;
import com.example.reto2_chat_server.chat.model.DAO.MessageDAO;
import com.example.reto2_chat_server.chat.model.DAO.UserChatDAO;
import com.example.reto2_chat_server.chat.model.DAO.User_ChatDAO;
import com.example.reto2_chat_server.chat.model.DAO.User_ChatIdDAO;
import com.example.reto2_chat_server.chat.model.convertidorDeClases.ConvertidorClases;
import com.example.reto2_chat_server.chat.model.serviceModel.ChatServiceModel;
import com.example.reto2_chat_server.chat.model.serviceModel.MessageServiceModel;
import com.example.reto2_chat_server.chat.model.serviceModel.UserChatServiceModel;
import com.example.reto2_chat_server.chat.model.serviceModel.User_ChatIdServiceModel;
import com.example.reto2_chat_server.chat.model.serviceModel.User_ChatServiceModel;
import com.example.reto2_chat_server.chat.repository.ChatRepository;
import com.example.reto2_chat_server.chat.repository.UserChatRepository;

@Service
public class ChatServiveImpl implements ChatService{
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UserChatRepository userRepository;
	
	@Override
	public List<ChatServiceModel> getChats(int id) {
	    List<ChatServiceModel> response = new ArrayList<>();
	    List<ChatDAO> chatList = new ArrayList<>();

	    Optional<UserChatDAO> userChats = userRepository.findById(id);

	    if (userChats.isPresent()) {
	        List<User_ChatDAO> userChatsList = userChats.get().getChats();
	        List<Integer> chatIds = userChatsList.stream()
	                .map(userChat -> userChat.getChat().getId())
	                .collect(Collectors.toList());
	        chatList = (List<ChatDAO>) chatRepository.findAllById(chatIds);
	        for (ChatDAO chatDAO : chatList) {
	        	response.add(deDAOaService(chatDAO));
			}
	        
	    }

	    return response;
	}

	
	private ChatServiceModel deDAOaService(ChatDAO chatDAO) {
		ChatServiceModel chat = new ChatServiceModel(
				chatDAO.getId(),
				chatDAO.isPublic(),
				chatDAO.getName(),
				deDAOaServiceMesages(chatDAO.getMesages()),
				deDAOaServiceUsers(chatDAO.getUsers())				
				);
		return chat;
	}


	private List<User_ChatServiceModel> deDAOaServiceUsers(List<User_ChatDAO> users) {
		List<User_ChatServiceModel> listUserService = new ArrayList<User_ChatServiceModel>();
		for (User_ChatDAO user_ChatDAO : users) {
			User_ChatServiceModel userService = new User_ChatServiceModel(
					deDAOaServiceId(user_ChatDAO.getId()),
					deDAOaServiceUser(user_ChatDAO.getUser()),
					user_ChatDAO.isAdmin()				
				);
			listUserService.add(userService);
		}
		
		return listUserService;
	}


	private UserChatServiceModel deDAOaServiceUser(UserChatDAO user) {
		UserChatServiceModel serviceUser = new UserChatServiceModel(
					user.getId(),
					user.getName()	
				);
		return serviceUser;
	}


	private User_ChatIdServiceModel deDAOaServiceId(User_ChatIdDAO id) {
		User_ChatIdServiceModel idService = new User_ChatIdServiceModel(
				id.getChatId(),
				id.getUserId()
			);		
		return idService;
	}


	private List<MessageServiceModel> deDAOaServiceMesages(List<MessageDAO> mesages) {
		List<MessageServiceModel> response = new ArrayList<MessageServiceModel>();
		for (MessageDAO messageDAO : mesages) {
			MessageServiceModel messageService = new MessageServiceModel(
					messageDAO.getId(),
					messageDAO.getDataType(),
					messageDAO.getContent(),
					messageDAO.getCreatedAt(),
					messageDAO.getUser_id()
					);
			
					
			response.add(messageService);
		}
		return response;
	}


	private UserChatServiceModel deDAOaServiceUserSinChat(UserChatDAO user) {
		UserChatServiceModel response = new UserChatServiceModel(
				user.getId(),
				user.getName()				
				);
		
		
		return response;
	}


	public List<Integer> getChatIds(int id) {
	    List<Integer> response = new ArrayList<>();
	    
	    Optional<UserChatDAO> userChats = userRepository.findById(id);
	    
	    if (userChats.isPresent()) {
	        List<User_ChatDAO> userChatsList = userChats.get().getChats();
	        response = userChatsList.stream()
	                .map(userChat -> userChat.getChat().getId())
	                .collect(Collectors.toList());
	    }
	    
	    return response;
	}
}
