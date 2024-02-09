package com.example.reto2_chat_server.chat.service;


import java.util.Date;
import java.util.List;

import com.example.reto2_chat_server.model.message.MessageServiceModel;

public class ChatServiceModel {
	private int id;
	private boolean isPublic;
	private String name;
	private Date createdAt;
	private Date updatedAt;
	private Integer idRoom;
	public Integer getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(Integer idRoom) {
		this.idRoom = idRoom;
	}
	private List<MessageServiceModel> listMessages;
    private List<UsersFromChatServiceModel> listUsers;
	
	public ChatServiceModel(int id, boolean isPublic, String name, Date createdAt, Date updatedAt) {
		super();
		this.id = id;
		this.isPublic = isPublic;
		this.name = name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public ChatServiceModel(int id, boolean isPublic, String name, Date createdAt, Date updatedAt, List<MessageServiceModel> listMessages,
			List<UsersFromChatServiceModel> listUsers) {
		super();
		this.id = id;
		this.isPublic = isPublic;
		this.name = name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.listMessages = listMessages;
		this.listUsers = listUsers;
	}
	public ChatServiceModel(int id) {
		super();
		this.id = id;
	}
	
	
	public ChatServiceModel(int id, boolean isPublic, String name) {
		super();
		this.id = id;
		this.isPublic = isPublic;
		this.name = name;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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
