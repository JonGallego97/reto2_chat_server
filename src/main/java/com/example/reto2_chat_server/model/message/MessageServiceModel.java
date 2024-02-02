package com.example.reto2_chat_server.model.message;

import java.util.Date;

import com.example.reto2_chat_server.security.user.service.UserServiceModel;


public class MessageServiceModel {
	private int id;	
	private DataType dataType;
	
	
	private String content;
	
	private Date createdAt;
	private Date updatedAt;


	private UserServiceModel userId;

	
	

	public MessageServiceModel(int id, DataType dataType, String content, Date createdAt, Date updatedAt,
			UserServiceModel userId) {
		super();
		this.id = id;
		this.dataType = dataType;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userId = userId;
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

	public MessageServiceModel() {
		super();
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
	public UserServiceModel getUserId() {
		return userId;
	}
	public void setUserId(UserServiceModel userId) {
		this.userId = userId;
	}





    
    

}
