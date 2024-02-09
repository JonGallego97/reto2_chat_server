package com.example.reto2_chat_server.chat.repository;

import java.util.Date;

public class InsertUserChat {
	private int userId;
	private int chatId;
	private boolean aIsAdmin;
	private Date createdAt;
	private Date updatesAt;
	@Override
	public String toString() {
		return "InsertUserChat [userId=" + userId + ", chatId=" + chatId + ", aIsAdmin=" + aIsAdmin + ", createdAt="
				+ createdAt + ", updatesAt=" + updatesAt + "]";
	}
	public InsertUserChat(int userId, int chatId, boolean aIsAdmin, Date createdAt, Date updatesAt) {
		super();
		this.userId = userId;
		this.chatId = chatId;
		this.aIsAdmin = aIsAdmin;
		this.createdAt = createdAt;
		this.updatesAt = updatesAt;
	}
	public InsertUserChat() {
		super();
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getChatId() {
		return chatId;
	}
	public void setChatId(int chatId) {
		this.chatId = chatId;
	}
	
	public boolean isaIsAdmin() {
		return aIsAdmin;
	}
	public void setaIsAdmin(boolean aIsAdmin) {
		this.aIsAdmin = aIsAdmin;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatesAt() {
		return updatesAt;
	}
	public void setUpdatesAt(Date updatesAt) {
		this.updatesAt = updatesAt;
	}
	
	
	
	
	
}
