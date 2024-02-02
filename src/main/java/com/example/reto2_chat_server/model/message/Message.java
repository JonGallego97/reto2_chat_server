package com.example.reto2_chat_server.model.message;

import java.util.Date;

import com.example.reto2_chat_server.chat.repository.Chat;
import com.example.reto2_chat_server.security.user.repository.UserDAO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "messages")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	@Column(name = "dataType")
	@NotNull
	@Enumerated(EnumType.STRING)
	private DataType dataType;
	
	
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;

	@ManyToOne
    @JoinColumn(name = "user_id")
	private UserDAO userId;
	@ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
	
	
	
	public Chat getChat() {
		return chat;
	}



	public void setChat(Chat chat) {
		this.chat = chat;
	}






	public Message(int id, @NotNull DataType dataType, String content, Date createdAt, Date updatedAt, UserDAO userId) {
		super();
		this.id = id;
		this.dataType = dataType;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userId = userId;
	}



	public Message(int id, @NotNull DataType dataType, String content, Date createdAt, Date updatedAt, UserDAO userId,
			Chat chat) {
		super();
		this.id = id;
		this.dataType = dataType;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.userId = userId;
		this.chat = chat;
	}



	@Override
	public String toString() {
		return "Message [id=" + id + ", dataType=" + dataType + ", content=" + content + ", createdAt=" + createdAt
				+ ", userId=" + userId + "]";
	}



	public Message() {
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public UserDAO getUserId() {
		return userId;
	}
	public void setUserId(UserDAO userId) {
		this.userId = userId;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
