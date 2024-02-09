package com.example.reto2_chat_server.chat.service;

public class DeleteChat {
	private int chatId;

	@Override
	public String toString() {
		return "DeleteChat [chatId=" + chatId + "]";
	}

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public DeleteChat(int chatId) {
		super();
		this.chatId = chatId;
	}

	public DeleteChat() {
		super();
	}
	
	
}
