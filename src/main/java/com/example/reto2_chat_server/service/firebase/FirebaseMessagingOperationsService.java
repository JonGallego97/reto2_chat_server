package com.example.reto2_chat_server.service.firebase;

import java.util.List;

public interface FirebaseMessagingOperationsService {
	void sendMulticastNotification(List deviceTokens);
}