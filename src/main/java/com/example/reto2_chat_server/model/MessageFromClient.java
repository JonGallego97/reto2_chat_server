package com.example.reto2_chat_server.model;

public class MessageFromClient {
	private String room;
	private String message;
	public MessageFromClient() {
		super();
		
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageFromClient(String room, String message) {
		super();
		this.room = room;
		this.message = message;
	}

}
