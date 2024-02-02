package com.example.reto2_chat_server.chat.repository;

public class UserInfo {
    private String email;
    private int userId;

    public UserInfo(String email, int userId) {
        this.email = email;
        this.userId = userId;
    }
    
    public UserInfo() {
    }

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
        return "UserInfo{" +
                "email='" + email + '\'' +
                ", userId=" + userId +
                '}';
    }
}
