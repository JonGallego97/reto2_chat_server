package com.example.reto2_chat_server.chat.model.serviceModel;

public class User_ChatIdServiceModel {
	
	private int chatId;
	private int userId;
	
	@Override
	public String toString() {
		return "User_ChatId [chatId=" + chatId + ", userId=" + userId + "]";
	}
	public User_ChatIdServiceModel(int chatId, int userId) {
		super();
		this.chatId = chatId;
		this.userId = userId;
	}
	public User_ChatIdServiceModel() {
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
