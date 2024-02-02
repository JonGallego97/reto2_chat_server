package com.example.reto2_chat_server.chat.repository;

public class UserInfoDao {
	private String email;
    private int userId;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

	@Override
	public String toString() {
		return "UserInfoDao [email=" + email + ", userId=" + userId + "]";
	}

	public UserInfoDao(String email, int userId) {
		super();
		this.email = email;
		this.userId = userId;
	}

	public UserInfoDao() {
		super();
	}

    
    
}
