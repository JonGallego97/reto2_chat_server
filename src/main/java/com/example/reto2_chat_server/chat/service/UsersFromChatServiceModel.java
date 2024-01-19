package com.example.reto2_chat_server.chat.service;

public class UsersFromChatServiceModel {
	
	private ForeignKeysFromChatsService id;
	private UserChatServiceModel user;
	private boolean isAdmin;

	
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public UsersFromChatServiceModel(ForeignKeysFromChatsService id, UserChatServiceModel user, boolean isAdmin) {
		super();
		this.id = id;
		this.user = user;
		this.isAdmin = isAdmin;
	}
	public UsersFromChatServiceModel(ForeignKeysFromChatsService id, UserChatServiceModel user) {
		super();
		this.id = id;
		this.user = user;
	}
	public ForeignKeysFromChatsService getId() {
		return id;
	}
	public void setId(ForeignKeysFromChatsService id) {
		this.id = id;
	}
	public UserChatServiceModel getUser() {
		return user;
	}
	public void setUser(UserChatServiceModel user) {
		this.user = user;
	}
	public UsersFromChatServiceModel() {
		super();
	}
	
	

	
}
