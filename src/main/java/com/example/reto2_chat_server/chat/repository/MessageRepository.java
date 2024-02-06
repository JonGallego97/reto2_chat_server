package com.example.reto2_chat_server.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.reto2_chat_server.model.message.Message;

public interface MessageRepository  extends CrudRepository<Message, Integer> {

	
	@Query("SELECT m.id FROM Message m where m.chat.id = :id")
	List<Integer> findByChatId(@Param("id")Integer id);

}
