package com.example.reto2_chat_server.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reto2_chat_server.chat.repository.Chat;
import com.example.reto2_chat_server.chat.repository.ChatRepository;
import com.example.reto2_chat_server.chat.repository.ForeignKeysFromChatsDAO;
import com.example.reto2_chat_server.chat.repository.UserChatsDAO;
import com.example.reto2_chat_server.chat.repository.UserChatRepository;
import com.example.reto2_chat_server.chat.repository.UsersFromChatDAO;
import com.example.reto2_chat_server.model.message.Message;
import com.example.reto2_chat_server.model.message.MessageServiceModel;

@Service
public class ChatServiveImpl implements ChatService{
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UserChatRepository userRepository;
	
	@Override
	public List<ChatServiceModel> getChats(int id) {
		
		
	    List<ChatServiceModel> response = new ArrayList<>();
	    List<Chat> chatList = new ArrayList<>();

	    Optional<UserChatsDAO> userChats = userRepository.findById(id);

	    if (userChats.isPresent()) {
	        List<UsersFromChatDAO> userChatsList = userChats.get().getChats();
	        List<Integer> chatIds = userChatsList.stream()
	                .map(userChat -> userChat.getChat().getId())
	                .collect(Collectors.toList());
	        chatList = (List<Chat>) chatRepository.findAllById(chatIds);
	        for (Chat chatDAO : chatList) {
	        	response.add(deDAOaService(chatDAO));
			}
	        
	    }

	    return response;
	}

	
	public List<Integer> getChatIds(int id) {
	    List<Integer> response = new ArrayList<>();
	    
	    Optional<UserChatsDAO> userChats = userRepository.findById(id);
	    
	    if (userChats.isPresent()) {
	        List<UsersFromChatDAO> userChatsList = userChats.get().getChats();
	        response = userChatsList.stream()
	                .map(userChat -> userChat.getChat().getId())
	                .collect(Collectors.toList());
	    }
	    
	    return response;
	}
	
	private ChatServiceModel deDAOaService(Chat chatDAO) {
		ChatServiceModel chat = new ChatServiceModel(
				chatDAO.getId(),
				chatDAO.isPublic(),
				chatDAO.getName(),
				deDAOaServiceMessages(chatDAO.getMessages()),
				deDAOaServiceUsers(chatDAO.getUsers())				
				);
		return chat;
	}
	private List<MessageServiceModel> deDAOaServiceMessages(List<Message> messages) {
		List<MessageServiceModel> response = new ArrayList<MessageServiceModel>();
		for (Message messageDAO : messages) {
			MessageServiceModel messageService = new MessageServiceModel(
					messageDAO.getId(),
					messageDAO.getDataType(),
					//TODO poner la imagen bien
					null,
					messageDAO.getCreatedAt(),
					messageDAO.getUserId().convertFromDAOtoServiceResumedForMessages(messageDAO.getUserId()));
				
					
			response.add(messageService);
		}
		return response;
	}
	
	private UserChatServiceModel deDAOaServiceUser(UserChatsDAO user) {
		UserChatServiceModel serviceUser = new UserChatServiceModel(
					user.getId(),
					user.getName(),
					user.getEmail()
					
				);
		return serviceUser;
	}
	
	private List<UsersFromChatServiceModel> deDAOaServiceUsers(List<UsersFromChatDAO> users) {
		List<UsersFromChatServiceModel> listUserService = new ArrayList<UsersFromChatServiceModel>();
		for (UsersFromChatDAO user_ChatDAO : users) {
			UsersFromChatServiceModel userService = new UsersFromChatServiceModel(
					deDAOaServiceId(user_ChatDAO.getId()),
					deDAOaServiceUser(user_ChatDAO.getUser()),
					user_ChatDAO.isAdmin()				
				);
			listUserService.add(userService);
		}
		
		return listUserService;
	}
	
	private ForeignKeysFromChatsService deDAOaServiceId(ForeignKeysFromChatsDAO id) {
		ForeignKeysFromChatsService idService = new ForeignKeysFromChatsService(
				id.getChatId(),
				id.getUserId()
			);		
		return idService;
	}
	
	
}
