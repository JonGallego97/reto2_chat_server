package com.example.reto2_chat_server.chat.repository;

import java.util.List;
import com.example.reto2_chat_server.model.message.Message;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "chats")
public class Chat {

	public Chat(int id) {
		super();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 60)
	private boolean isPublic;
	@Column(length = 60)
	private String name;
	
	@OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval =  true, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Message> messages;
	
	@OneToMany(mappedBy = "chat")
	private List<UsersFromChatDAO> users;

	public Chat(int id, boolean isPublic, String name, List<Message> messages, List<UsersFromChatDAO> users) {
		super();
		this.id = id;
		this.isPublic = isPublic;
		this.name = name;
		this.messages = messages;
		this.users = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<UsersFromChatDAO> getUsers() {
		return users;
	}

	public void setUsers(List<UsersFromChatDAO> users) {
		this.users = users;
	}

	public Chat() {
		super();
	}

	@Override
	public String toString() {
		return "Chat [id=" + id + ", isPublic=" + isPublic + ", name=" + name + ", messages=" + messages + ", users="
				+ users + "]";
	}
	

	
	
	
	
	
}
