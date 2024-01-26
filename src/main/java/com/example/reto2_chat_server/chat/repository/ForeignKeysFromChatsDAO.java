package com.example.reto2_chat_server.chat.repository;

import java.util.Objects;

import jakarta.persistence.Column;

public class ForeignKeysFromChatsDAO {
		@Column(name = "chat_Id")
		private int chatId;
		@Column(name = "user_Id")
		private int userId;
		public ForeignKeysFromChatsDAO(int chatId, int userId) {
			super();
			this.chatId = chatId;
			this.userId = userId;
		}
		public ForeignKeysFromChatsDAO() {
			super();
		}
		public int getChatId() {
			return chatId;
		}
		public void setChatId(int chatId) {
			this.chatId = chatId;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		@Override
		public String toString() {
			return "ForeignKeysFromChatsDAO [chatId=" + chatId + ", userId=" + userId + "]";
		}
		@Override
		public int hashCode() {
			return Objects.hash(chatId, userId);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ForeignKeysFromChatsDAO other = (ForeignKeysFromChatsDAO) obj;
			return chatId == other.chatId && userId == other.userId;
		}
		
		
	
		
		
	}


