package com.example.reto2_chat_server.config.socketio;

public enum SocketEvents {
	ON_MESSAGE_RECEIVED("chat message"),
	ON_SEND_MESSAGE("chat message"),
    ON_ADD_USER_CHAT_SEND("add user chat send"),
    ON_ADD_USER_CHAT_RECIVE("add user chat recive"),
	ON_SEND_ID_MESSAGE("chat message id"),
    ON_DELETE_USER_CHAT_SEND("delete user chat send"),
    ON_DELETE_USER_CHAT_RECIVE("delete user chat recive"),
    ON_CREATE_CHAT_SEND("create chat send"),
    ON_CREATE_CHAT_RECIVE("create chat recive"),
	ON_DELETE_CHAT_SEND("delete chat send"),
    ON_DELETE_CHAT_RECIVE("delete chat recive");
	
	public final String value;

	private SocketEvents(String value) {
		this.value = value;
	}

}
