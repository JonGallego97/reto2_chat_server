package com.example.reto2_chat_server.model;

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
public class UserChat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 60)
	private String name;
	
	@OneToMany(mappedBy = "user")
	private List<User_Chat> chats;

	


	@Override
	public String toString() {
		return "UserChat [id=" + id + ", name=" + name + ", chats=" + chats + "]";
	}

	public UserChat(int id, String name, List<User_Chat> chats) {
		super();
		this.id = id;
		this.name = name;
		this.chats = chats;
	}

	public UserChat() {
		super();
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

	public List<User_Chat> getChats() {
		return chats;
	}

	public void setChats(List<User_Chat> chats) {
		this.chats = chats;
	}
	


	
	
	
}
