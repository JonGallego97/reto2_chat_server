package com.example.reto2_chat_server.model;

import jakarta.persistence.Column;

public class User_ChatId {
	
	@Column(name = "chat_Id")
	private int chatId;
	@Column(name = "user_Id")
	private int userId;
	@Override
	public String toString() {
		return "User_ChatId [chatId=" + chatId + ", userId=" + userId + "]";
	}
	public User_ChatId(int chatId, int userId) {
		super();
		this.chatId = chatId;
		this.userId = userId;
	}
	public User_ChatId() {
		super();
	}
	public int getChatId() {
		return chatId;
	}
	public void setChatId(int chatId) {
		this.chatId = chatId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
