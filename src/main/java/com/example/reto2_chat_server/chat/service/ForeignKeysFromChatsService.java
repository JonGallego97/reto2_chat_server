package com.example.reto2_chat_server.chat.service;

public class ForeignKeysFromChatsService {
	private int chatId;
	private int userId;
	public ForeignKeysFromChatsService(int chatId, int userId) {
		super();
		this.chatId = chatId;
		this.userId = userId;
	}
	public ForeignKeysFromChatsService() {
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
