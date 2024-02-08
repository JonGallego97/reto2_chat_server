package com.example.reto2_chat_server.model;

public class CrateChat {
    private int userId;
	private String name;
	private int roomChatid;
	private boolean aIsPublic;
	@Override
	public String toString() {
		return "CrateChat [userId=" + userId + ", name=" + name + ", roomChatid=" + roomChatid + ", aIsPublic="
				+ aIsPublic + "]";
	}
	public CrateChat(int userId, String name, int roomChatid, boolean aIsPublic) {
		super();
		this.userId = userId;
		this.name = name;
		this.roomChatid = roomChatid;
		this.aIsPublic = aIsPublic;
	}
	public CrateChat() {
		super();
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
	public int getRoomChatid() {
		return roomChatid;
	}
	public void setRoomChatid(int roomChatid) {
		this.roomChatid = roomChatid;
	}
	public boolean isaIsPublic() {
		return aIsPublic;
	}
	public void setaIsPublic(boolean aIsPublic) {
		this.aIsPublic = aIsPublic;
	}
	
}
