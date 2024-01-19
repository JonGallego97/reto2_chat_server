package com.example.reto2_chat_server.model.message;

public class MessageFromServer {
	private MessageType messageType;
	private String message;
	private String room;
	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public MessageFromServer(MessageType messageType, String message, String room, DataType dataType, Integer authorId,
			String authorName) {
		super();
		this.messageType = messageType;
		this.message = message;
		this.room = room;
		this.dataType = dataType;
		this.authorId = authorId;
		this.authorName = authorName;
	}
	private DataType dataType;
	private Integer authorId;
	private String authorName;
	public MessageFromServer() {
		super();
	}
	@Override
	public String toString() {
		return "MessageFromServer [messageType=" + messageType + ", message=" + message + ", room=" + room
				+ ", dataType=" + dataType + ", authorId=" + authorId + ", authorName=" + authorName + "]";
	}
	
	

}
