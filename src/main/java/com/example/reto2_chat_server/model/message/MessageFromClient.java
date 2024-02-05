package com.example.reto2_chat_server.model.message;

public class MessageFromClient {
	private int idRoom;
	private String room;
	private String message;
	private DataType type;
	
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
	public MessageFromClient(String room, String message, int idRoom, DataType type) {
		super();
		this.idRoom = idRoom;
		this.room = room;
		this.message = message;
		this.type = type;
	}
	@Override
	public String toString() {
		return "MessageFromClient [idRoom=" + idRoom + ", room=" + room + ", message=" + message + ", type=" + type + "]";
	}
	public int getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}


	
}
