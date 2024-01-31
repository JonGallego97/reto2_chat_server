package com.example.reto2_chat_server.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;


public interface UserChatRepository  extends CrudRepository<UserChatsDAO, Integer> {

	
	@Query("SELECT u.email FROM users u WHERE u.id NOT IN (SELECT uc.user_id FROM user_chat uc WHERE ufc.chat_id = :chatId)")
    List<String> findUsersEmailsNotInChat(@Param("chatId") Integer chatId);
}	
