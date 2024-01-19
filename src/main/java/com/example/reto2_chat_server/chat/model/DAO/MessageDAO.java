package com.example.reto2_chat_server.chat.model.DAO;

import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;

import com.example.reto2_chat_server.model.DataType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "messages")
public class MessageDAO {
	public ChatDAO getChat() {
		return chat;
	}



	public void setChat(ChatDAO chat) {
		this.chat = chat;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	@Column(name = "dataType")
	@NotNull
	@Enumerated(EnumType.STRING)
	private DataType dataType;
	
	@Column(name = "content", columnDefinition="BLOB")
	private Blob content;
	@Column(name = "created_at")
	private Date createdAt;
	@Column(name = "user_id")
	private int user_id;
	@ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatDAO chat;
	
	
	
	public MessageDAO(int id, DataType dataType, Blob content, Date createdAt, int user_id) {
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
				+ ", userId=" + user_id + "]";
	}



	public MessageDAO() {
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



	public int getUser_id() {
		return user_id;
	}



	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	
	
}
