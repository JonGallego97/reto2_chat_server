package com.example.reto2_chat_server.config.socketio;

public enum SocketEvents {
	ON_MESSAGE_RECEIVED("chat message"),
	ON_SEND_MESSAGE("chat message"),
	ON_SEND_ID_MESSAGE("chat message id");
	public final String value;

	private SocketEvents(String value) {
		this.value = value;
	}

}
