package com.example.reto2_chat_server.chat.service;

import java.util.List;
import java.util.Optional;

import com.example.reto2_chat_server.chat.model.DAO.ChatDAO;
import com.example.reto2_chat_server.chat.model.serviceModel.ChatServiceModel;

public interface ChatService {

	List<ChatServiceModel> getChats(int id);
}
