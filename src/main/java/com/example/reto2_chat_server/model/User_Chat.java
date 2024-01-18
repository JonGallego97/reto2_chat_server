package com.example.reto2_chat_server.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class User_Chat {
	@EmbeddedId
	private User_ChatId id;
	
	@ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
	private UserChat user;
	
	@ManyToOne
    @MapsId("chatId")
    @JoinColumn(name = "chat_id")
	private Chat chat;
	
	@Column
	private boolean isAdmin;

	public User_ChatId getId() {
		return id;
	}

	public void setId(User_ChatId id) {
		this.id = id;
	}

	public UserChat getUser() {
		return user;
	}

	public void setUser(UserChat user) {
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
	
}
