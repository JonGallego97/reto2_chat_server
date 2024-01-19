package com.example.reto2_chat_server.chat.repository;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "User_Chat")
public class UsersFromChatDAO {
	@EmbeddedId
	private ForeignKeysFromChatsDAO id;
	
	@JsonIgnore
	@ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
	private UserChatsDAO user;
	
	@JsonIgnore
	@ManyToOne
    @MapsId("chatId")
    @JoinColumn(name = "chat_id")
	private Chat chat;
	
	@Column
	private boolean isAdmin;

	public UsersFromChatDAO(ForeignKeysFromChatsDAO id, UserChatsDAO user, Chat chat, boolean isAdmin) {
		super();
		this.id = id;
		this.user = user;
		this.chat = chat;
		this.isAdmin = isAdmin;
	}

	public UsersFromChatDAO() {
		super();
	}

	public ForeignKeysFromChatsDAO getId() {
		return id;
	}

	public void setId(ForeignKeysFromChatsDAO id) {
		this.id = id;
	}

	public UserChatsDAO getUser() {
		return user;
	}

	public void setUser(UserChatsDAO user) {
		this.user = user;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "UsersFromChatDAO [id=" + id + ", user=" + user + ", chat=" + chat + ", isAdmin=" + isAdmin + "]";
	}

	
	
}