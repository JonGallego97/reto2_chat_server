package com.example.reto2_chat_server.model;

public class CrateChat {
    private int userId;
	private String name;
	private int roomChatId;
	private boolean aIsPublic;
	@Override
	public String toString() {
		return "CrateChat [userId=" + userId + ", name=" + name + ", roomChatId=" + roomChatId + ", aIsPublic="
				+ aIsPublic + "]";
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRoomChatId() {
		return roomChatId;
	}
	public void setRoomChatId(int roomChatId) {
		this.roomChatId = roomChatId;
	}
	public boolean isaIsPublic() {
		return aIsPublic;
	}
	public void setaIsPublic(boolean aIsPublic) {
		this.aIsPublic = aIsPublic;
	}
	public CrateChat(int userId, String name, int roomChatId, boolean aIsPublic) {
		super();
		this.userId = userId;
		this.name = name;
		this.roomChatId = roomChatId;
		this.aIsPublic = aIsPublic;
	}
	public CrateChat() {
		super();
	}
	
	
}
