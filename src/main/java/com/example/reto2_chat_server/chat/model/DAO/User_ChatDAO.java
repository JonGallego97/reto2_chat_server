package com.example.reto2_chat_server.chat.model.DAO;

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
public class User_ChatDAO {
	@EmbeddedId
	private User_ChatIdDAO id;
	
	@JsonIgnore
	@ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
	private UserChatDAO user;
	
	@JsonIgnore
	@ManyToOne
    @MapsId("chatId")
    @JoinColumn(name = "chat_id")
	private ChatDAO chat;
	
	@Column
	private boolean isAdmin;

	@Override
    public String toString() {
        return "User_Chat [id=" + id + ", user=" + (user != null ? user.getName() : "null") +
                ", chat=" + (chat != null ? chat.getName() : "null") + ", isAdmin=" + isAdmin + "]";
    }
	
	public User_ChatIdDAO getId() {
		return id;
	}

	public void setId(User_ChatIdDAO id) {
		this.id = id;
	}

	public UserChatDAO getUser() {
		return user;
	}

	public void setUser(UserChatDAO user) {
		this.user = user;
	}

	public ChatDAO getChat() {
		return chat;
	}

	public void setChat(ChatDAO chat) {
		this.chat = chat;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
