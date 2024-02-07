package com.example.reto2_chat_server.chat.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.reto2_chat_server.chat.service.ChatShow;

public interface ChatRepository extends CrudRepository<Chat, Integer> {
	@Modifying
    @Query("DELETE FROM Chat c WHERE c.id = :id")
    void deleteChatById(@Param("id") int id);

	
	@Query("SELECT DISTINCT NEW com.example.reto2_chat_server.chat.service.ChatShow(c.id AS chatId, c.name AS name) " +
	           "FROM Chat c " +
	           "JOIN c.users u " +
	           "WHERE u.user.id = :id AND c.isPublic = true")
	List<ChatShow> findAllUserChatPublic(@Param("id") int id);


	@Query("SELECT DISTINCT NEW com.example.reto2_chat_server.chat.service.ChatShow(c.id AS chatId, c.name AS name) from Chat c where c.isPublic = true")
	List<ChatShow> findAllChatPublic(@Param("id") int id);
}