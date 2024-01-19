package com.example.reto2_chat_server.chat.service;

import java.util.List;

import com.example.reto2_chat_server.model.message.MessageServiceModel;

public class ChatServiceModel {
	private int id;
	private boolean isPublic;
	private String name;
	private List<MessageServiceModel> listMessages;
    private List<UsersFromChatServiceModel> listUsers;
	public ChatServiceModel(int id, boolean isPublic, String name, List<MessageServiceModel> listMessages,
			List<UsersFromChatServiceModel> listUsers) {
		super();
		this.id = id;
		this.isPublic = isPublic;
		this.name = name;
		this.listMessages = listMessages;
		this.listUsers = listUsers;
	}
	public ChatServiceModel() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<MessageServiceModel> getListMessages() {
		return listMessages;
	}
	public void setListMessages(List<MessageServiceModel> listMessages) {
		this.listMessages = listMessages;
	}
	public List<UsersFromChatServiceModel> getListUsers() {
		return listUsers;
	}
	public void setListUsers(List<UsersFromChatServiceModel> listUsers) {
		this.listUsers = listUsers;
	}
	
	
}
