package com.example.reto2_chat_server.chat.model.DAO;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserChatDAO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 60)
	private String name;
	
	@OneToMany(mappedBy = "user")
	private List<User_ChatDAO> chats;

	


	 @Override
	    public String toString() {
	        return "UserChat [id=" + id + ", name=" + name +
	                ", chats=" + (chats != null ? chats.stream().map(User_ChatDAO::getChat).map(ChatDAO::toString).collect(Collectors.toList()) : "null") +
	                "]";
	    }

	public UserChatDAO(int id, String name, List<User_ChatDAO> chats) {
		super();
		this.id = id;
		this.name = name;
		this.chats = chats;
	}
	
	public UserChatDAO(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public UserChatDAO() {
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

	public List<User_ChatDAO> getChats() {
		return chats;
	}

	public void setChats(List<User_ChatDAO> chats) {
		this.chats = chats;
	}



	
	
	
}
