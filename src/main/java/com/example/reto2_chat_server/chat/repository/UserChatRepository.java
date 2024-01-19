package com.example.reto2_chat_server.chat.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.reto2_chat_server.chat.model.DAO.UserChatDAO;
import com.example.reto2_chat_server.chat.model.DAO.User_ChatDAO;


public interface UserChatRepository  extends CrudRepository<UserChatDAO, Integer> {

}
