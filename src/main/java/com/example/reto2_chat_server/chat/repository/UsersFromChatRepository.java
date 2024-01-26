package com.example.reto2_chat_server.chat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UsersFromChatRepository  extends CrudRepository<UsersFromChatDAO, Integer> {

	List<UsersFromChatDAO> findByUser_Id(Integer userId);

}
