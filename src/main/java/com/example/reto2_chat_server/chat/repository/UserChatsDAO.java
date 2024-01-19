package com.example.reto2_chat_server.chat.repository;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserChatsDAO {
	public UserChatsDAO(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 60)
	private String name;
	private String email;
	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserChatsDAO(int id, String name, String email, List<UsersFromChatDAO> chats) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.chats = chats;
	}

	@OneToMany(mappedBy = "user")
	private List<UsersFromChatDAO> chats;

	public UserChatsDAO(int id, String name, List<UsersFromChatDAO> chats) {
		super();
		this.id = id;
		this.name = name;
		this.chats = chats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UsersFromChatDAO> getChats() {
		return chats;
	}

	public void setChats(List<UsersFromChatDAO> chats) {
		this.chats = chats;
	}

	public UserChatsDAO() {
		super();
	}

	@Override
	public String toString() {
		return "UserChatsDAO [id=" + id + ", name=" + name + ", chats=" + chats + "]";
	}

	


	
	
	
}
