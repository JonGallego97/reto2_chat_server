package com.example.reto2_chat_server.chat.model.serviceModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Blob;
import java.util.Date;

import com.example.reto2_chat_server.model.DataType;

public class MessageServiceModel {
	
	private int id;	
	private DataType dataType;
	@JsonIgnore
	private Blob content;
	
	private Date createdAt;
	private int user_id;
	
	
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public MessageServiceModel(int id, DataType dataType, Blob content, Date createdAt, int user_id) {
		super();
		this.id = id;
		this.dataType = dataType;
		this.content = content;
		this.createdAt = createdAt;
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", dataType=" + dataType + ", content=" + content + ", createdAt=" + createdAt
				 + "]";
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
