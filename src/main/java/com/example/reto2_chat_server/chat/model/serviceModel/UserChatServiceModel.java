package com.example.reto2_chat_server.chat.model.serviceModel;




public class UserChatServiceModel {
	private int id;
	private String name;
	

	


	 @Override
	    public String toString() {
	        return "UserChat [id=" + id + ", name=" + name +
	                
	                "]";
	    }

	public UserChatServiceModel(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	


	
	
	
}
