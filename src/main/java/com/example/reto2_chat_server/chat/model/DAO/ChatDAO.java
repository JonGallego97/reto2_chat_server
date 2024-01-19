package com.example.reto2_chat_server.chat.model.DAO;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChatDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 60)
	private boolean isPublic;
	@Column(length = 60)
	private String name;
	@OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval =  true, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<MessageDAO> mesages;
	@OneToMany(mappedBy = "chat")
	private List<User_ChatDAO> users;
	
	@Override
    public String toString() {
        return "Chat [id=" + id + ", isPublic=" + isPublic + ", name=" + name +
                ", mesages=" + mesages + ", users=" +
                (users != null ? users.stream().map(User_ChatDAO::toString).collect(Collectors.toList()) : "null") +
                "]";
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

	public List<MessageDAO> getMesages() {
		return mesages;
	}

	public void setMesages(List<MessageDAO> mesages) {
		this.mesages = mesages;
	}

	public List<User_ChatDAO> getUsers() {
		return users;
	}

	public void setUsers(List<User_ChatDAO> users) {
		this.users = users;
	}


	public ChatDAO(int id, boolean isPublic, String name, List<MessageDAO> mesages, List<User_ChatDAO> users) {
		super();
		this.id = id;
		this.isPublic = isPublic;
		this.name = name;
		this.mesages = mesages;
		this.users = users;
	}

	public ChatDAO() {
		super();
	}
	
	
	
	
	
}
