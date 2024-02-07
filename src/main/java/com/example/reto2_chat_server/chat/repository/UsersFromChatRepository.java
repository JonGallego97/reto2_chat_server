package com.example.reto2_chat_server.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.reto2_chat_server.chat.service.ChatShow;

public interface UsersFromChatRepository  extends CrudRepository<UsersFromChatDAO, Integer> {

	List<UsersFromChatDAO> findByUser_Id(Integer userId);

	@Query("SELECT u.user.id " +
    	       "FROM UsersFromChatDAO u " +
    	       "WHERE u.chat.id = :chatId")
    	List<Integer> findUserIdsByChatId(@Param("chatId") int chatId);
	
	@Query("SELECT DISTINCT NEW com.example.reto2_chat_server.chat.service.ChatShow(u.chat.id AS chatId, u.chat.name AS name) " +
		       "FROM UsersFromChatDAO u " +
		       "WHERE u.user.id != :id AND u.chat.isPublic = true")
		List<ChatShow> findAllUserChatPublic(@Param("id") int id);

}
