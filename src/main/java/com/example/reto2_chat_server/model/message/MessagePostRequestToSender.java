package com.example.reto2_chat_server.model.message;

public class MessagePostRequestToSender {
	private int idServer;
	private int idRoom;
	@Override
	public String toString() {
		return "MessagePostRequestToSender [idServer=" + idServer + ", idRoom=" + idRoom + "]";
	}
	public MessagePostRequestToSender(int idServer, int idRoom) {
		super();
		this.idServer = idServer;
		this.idRoom = idRoom;
	}
	public int getIdServer() {
		return idServer;
	}
	public void setIdServer(int idServer) {
		this.idServer = idServer;
	}
	public int getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}
	
}
