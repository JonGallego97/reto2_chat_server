package com.example.reto2_chat_server.chat.repository;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserChatRepository extends CrudRepository<UserChatsDAO, Integer> {
	
    @Query("SELECT u.email as email, u.id as userId FROM UserChatsDAO u")
    List<UserInfo> findAllEmails();

    @Query("SELECT u.email FROM UserChatsDAO u " +
            "JOIN u.chats c " +
            "WHERE c.chat.id = :chatId")
     List<String> findEmailsInChat(@Param("chatId") int chatId);
    
    @Query("SELECT u.email as email, u.id as userId FROM UserChatsDAO u " +
            "JOIN u.chats c " +
            "WHERE c.chat.id = :chatId")
     List<UserInfo> findUsersInChat(@Param("chatId") int chatId);

}
	
