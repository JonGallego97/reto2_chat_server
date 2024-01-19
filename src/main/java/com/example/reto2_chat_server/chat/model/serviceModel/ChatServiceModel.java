package com.example.reto2_chat_server.chat.model.serviceModel;

import java.util.List;
import java.util.stream.Collectors;


public class ChatServiceModel {

	private int id;
	private boolean isPublic;
	private String name;
	private List<MessageServiceModel> mesages;
	private List<User_ChatServiceModel> users;
	
	@Override
    public String toString() {
        return "Chat [id=" + id + ", isPublic=" + isPublic + ", name=" + name +
                ", mesages=" + mesages + ", users=" +
                (users != null ? users.stream().map(User_ChatServiceModel::toString).collect(Collectors.toList()) : "null") +
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

	public List<MessageServiceModel> getMesages() {
		return mesages;
	}

	public void setMesages(List<MessageServiceModel> mesages) {
		this.mesages = mesages;
	}

	public List<User_ChatServiceModel> getUsers() {
		return users;
	}

	public void setUsers(List<User_ChatServiceModel> users) {
		this.users = users;
	}


	public ChatServiceModel(int id, boolean isPublic, String name, List<MessageServiceModel> mesages, List<User_ChatServiceModel> users) {
		super();
		this.id = id;
		this.isPublic = isPublic;
		this.name = name;
		this.mesages = mesages;
		this.users = users;
	}

	public ChatServiceModel() {
		super();
	}
	
	
	
	
	
}
