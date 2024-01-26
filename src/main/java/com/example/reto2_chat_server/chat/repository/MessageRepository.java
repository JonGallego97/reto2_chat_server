package com.example.reto2_chat_server.chat.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.reto2_chat_server.model.message.Message;

public interface MessageRepository  extends CrudRepository<Message, Integer> {

}
