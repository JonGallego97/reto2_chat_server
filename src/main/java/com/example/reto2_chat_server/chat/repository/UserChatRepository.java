package com.example.reto2_chat_server.chat.repository;

import java.util.List;
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
    
    @Query("SELECT NEW com.example.reto2_chat_server.chat.repository.UserInfoDao(u.user.email, u.user.id) " +
    	       "FROM UsersFromChatDAO u " +
    	       "WHERE u.chat.id = :chatId AND u.isAdmin = false AND u.user.id != :userId order by u.user.email")
    	List<UserInfoDao> findNonAdminUsersInChat(@Param("chatId") int chatId, @Param("userId") int userId);

    @Query("SELECT NEW com.example.reto2_chat_server.chat.repository.UserInfoDao(u.user.email, u.user.id) " +
 	       "FROM UsersFromChatDAO u " +
 	       "WHERE u.chat.id = :chatId AND u.isAdmin = true order by u.user.email")
 	List<UserInfoDao> findAdminUsersInChat(@Param("chatId") int chatId);
    
    
    List<UserChatsDAO> findAllByOrderByEmail();

    @Query("SELECT COUNT(u) > 0 " +
    	       "FROM UsersFromChatDAO u " +
    	       "WHERE u.chat.id = :chatId AND u.user.id = :userId AND u.isAdmin = true")
    	boolean isAdminInChat(@Param("chatId") int chatId, @Param("userId") int userId);

  





}
	
