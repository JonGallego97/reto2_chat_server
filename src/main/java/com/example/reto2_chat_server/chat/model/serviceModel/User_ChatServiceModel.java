package com.example.reto2_chat_server.chat.model.serviceModel;


public class User_ChatServiceModel {
	private User_ChatIdServiceModel id;
	private UserChatServiceModel user;
	
	
	public User_ChatServiceModel(User_ChatIdServiceModel id, UserChatServiceModel user,
			boolean isAdmin) {
		super();
		this.id = id;
		this.user = user;
		this.isAdmin = isAdmin;
	}

	private boolean isAdmin;

	@Override
    public String toString() {
        return "User_Chat [id=" + id + ", user=" + (user != null ? user.getName() : "null") +
                ", isAdmin=" + isAdmin + "]";
    }
	
	public User_ChatIdServiceModel getId() {
		return id;
	}

	public void setId(User_ChatIdServiceModel id) {
		this.id = id;
	}

	public UserChatServiceModel getUser() {
		return user;
	}

	public void setUser(UserChatServiceModel user) {
		this.user = user;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
