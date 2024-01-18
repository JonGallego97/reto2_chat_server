package com.example.reto2_chat_server.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.reto2_chat_server.model.Chat;

public interface ChatRepository extends CrudRepository<Chat, Integer> {
}