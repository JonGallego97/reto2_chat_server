package com.example.reto2_chat_server.chat.service;


public class UserChatServiceModel {
	private int id;
	private String name;
	private String email;


	 public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserChatServiceModel(int id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	@Override
	    public String toString() {
	        return "UserChat [id=" + id + ", name=" + name +
	                
	                "]";
	    }

	public UserChatServiceModel(int id) {
		super();
		this.id = id;
	}

	public UserChatServiceModel() {
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

	public UserChatServiceModel(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	


	
	
	
}