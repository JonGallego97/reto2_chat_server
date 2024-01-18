package com.example.reto2_chat_server.chat.service;

import java.util.List;
import java.util.Optional;

import com.example.reto2_chat_server.model.Chat;

public interface ChatService {

	List<Chat> getChats(int id);
}
