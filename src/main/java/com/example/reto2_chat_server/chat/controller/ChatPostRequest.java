package com.example.reto2_chat_server.chat.controller;



public class ChatPostRequest {


	private boolean isPublic;
	private String name;
	public ChatPostRequest(boolean isPublic, String name) {
		super();
		this.isPublic = isPublic;
		this.name = name;
	}
	public ChatPostRequest() {
		super();
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

	
}
