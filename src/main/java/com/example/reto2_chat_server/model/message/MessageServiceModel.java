package com.example.reto2_chat_server.model.message;

import java.sql.Blob;
import java.util.Date;

import com.example.reto2_chat_server.security.user.service.UserServiceModel;


public class MessageServiceModel {
	private int id;	
	private DataType dataType;
	
	
	private Blob content;
	
	private Date createdAt;

	private UserServiceModel userId;

	
	public MessageServiceModel(int id, DataType dataType, Blob content, Date createdAt, UserServiceModel userId) {
		super();
		this.id = id;
		this.dataType = dataType;
		this.content = content;
		this.createdAt = createdAt;
		this.userId = userId;
	}



	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
	public Blob getContent() {
		return content;
	}
	public void setContent(Blob content) {
		this.content = content;
	}
	public UserServiceModel getUserId() {
		return userId;
	}
	public void setUserId(UserServiceModel userId) {
		this.userId = userId;
	}





    
    

}
