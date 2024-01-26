package com.example.reto2_chat_server.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;


public interface UserChatRepository  extends CrudRepository<UserChatsDAO, Integer> {

}
