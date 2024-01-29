package com.example.reto2_chat_server.model.message;

public class MessageFromClient {
	private int idRoom;
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
	public MessageFromClient(String room, String message, int idRoom) {
		super();
		this.idRoom = idRoom;
		this.room = room;
		this.message = message;
	}
	@Override
	public String toString() {
		return "MessageFromClient [idRoom=" + idRoom + ", room=" + room + ", message=" + message + "]";
	}
	public int getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}


	
}
