package com.example.reto2_chat_server.chat.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.reto2_chat_server.model.UserChat;
import com.example.reto2_chat_server.model.User_Chat;


public interface UserChatRepository  extends CrudRepository<UserChat, Integer> {

}
