package com.example.reto2_chat_server.chat.controller;

public class UsersFromChatsPostRequest {
	
	@Override
	public String toString() {
		return "UsersFromChatsPostRequest [userId=" + userId + ", chatId=" + chatId + ", isAdmin=" + isAdmin + "]";
	}


	private Integer userId;
	
	private Integer chatId;
	
	private boolean isAdmin;

	
	public UsersFromChatsPostRequest(Integer userId, Integer chatId, boolean isAdmin) {
		super();
		this.userId = userId;
		this.chatId = chatId;
		this.isAdmin = isAdmin;
	}

	public UsersFromChatsPostRequest() {
		super();
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Integer getChatId() {
		return chatId;
	}


	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}


	public boolean isAdmin() {
		return isAdmin;
	}


	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	
	
}


