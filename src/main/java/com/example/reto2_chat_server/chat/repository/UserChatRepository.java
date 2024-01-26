package com.example.reto2_chat_server.chat.repository;


import org.springframework.data.repository.CrudRepository;


public interface UserChatRepository  extends CrudRepository<UserChatsDAO, Integer> {

}
