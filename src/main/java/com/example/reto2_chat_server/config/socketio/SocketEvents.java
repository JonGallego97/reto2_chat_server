package com.example.reto2_chat_server.config.socketio;

public enum SocketEvents {
	ON_MESSAGE_RECEIVED("chat message"),
	ON_SEND_MESSAGE("chat message"),
    ON_ADD_USER_CHAT_SEND("add user chat send"),
    ON_ADD_USER_CHAT_RECIVE("add user chat recive"),
	ON_SEND_ID_MESSAGE("chat message id");
	public final String value;

	private SocketEvents(String value) {
		this.value = value;
	}

}
