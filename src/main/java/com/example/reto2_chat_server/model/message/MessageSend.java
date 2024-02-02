package com.example.reto2_chat_server.model.message;

import java.util.Date;

import com.example.reto2_chat_server.chat.service.ChatServiceModel;
import com.example.reto2_chat_server.security.user.service.UserServiceModel;

public class MessageSend {
	private int id;	
	private DataType dataType;	
	private String content;
	private Date createdAt;
	private Date updatedAt;

	private UserServiceModel userId;
    private ChatServiceModel chat;

    
	@Override
	public String toString() {
		return "MessageSend [id=" + id + ", dataType=" + dataType + ", content=" + content + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", userId=" + userId + ", chat=" + chat + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public UserServiceModel getUserId() {
		return userId;
	}
	public void setUserId(UserServiceModel userId) {
		this.userId = userId;
	}
	public ChatServiceModel getChat() {
		return chat;
	}
	public void setChat(ChatServiceModel chat) {
		this.chat = chat;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public MessageSend(int id, DataType dataType, String content, Date createdAt, Date updatedAt,
			UserServiceModel userId, ChatServiceModel chat) {
		super();
		this.id = id;
		this.dataType = dataType;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userId = userId;
		this.chat = chat;
	}
	public MessageSend() {
		super();
	}
    
	
    
}
