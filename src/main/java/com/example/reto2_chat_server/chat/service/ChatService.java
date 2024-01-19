package com.example.reto2_chat_server.chat.service;

import java.util.List;


public interface ChatService {

	List<ChatServiceModel> getChats(int id);
}
