package com.example.reto2_chat_server.chat.service;

import com.example.reto2_chat_server.model.message.Message;
import com.example.reto2_chat_server.model.message.MessageSend;

public interface MessageService {
	Message insertMessage(MessageSend message);
}

