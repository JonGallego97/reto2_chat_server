package com.example.reto2_chat_server.chat.service;

public class ChatShow {
	
	private int chatId;
	private String name;
	@Override
	public String toString() {
		return "ChatShow [chatId=" + chatId + ", name=" + name + "]";
	}
	public int getChatId() {
		return chatId;
	}
	public void setChatId(int chatId) {
		this.chatId = chatId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ChatShow(int chatId, String name) {
		super();
		this.chatId = chatId;
		this.name = name;
	}
	public ChatShow() {
		super();
	}

	
	
}
