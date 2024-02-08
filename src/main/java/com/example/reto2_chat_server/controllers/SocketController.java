package com.example.reto2_chat_server.controllers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.reto2_chat_server.config.socketio.SocketEvents;
import com.example.reto2_chat_server.config.socketio.SocketIOConfig;
import com.example.reto2_chat_server.model.message.DataType;
import com.example.reto2_chat_server.model.message.MessageFromServer;
import com.example.reto2_chat_server.model.message.MessageType;
import com.example.reto2_chat_server.service.firebase.FirebaseMessagingOperationsService;

@RestController
@RequestMapping("/api/sockets")
public class SocketController {
	private final SocketIOServer socketIOServer;

	 @Autowired
	 private FirebaseMessagingOperationsService firebaseMessagingOperationsService;
	 
	
	public SocketController(SocketIOServer socketIOServer) {
		this.socketIOServer = socketIOServer;
		
		
	}
	
    @GetMapping("/send-firebase-message")
    public String sendFirebaseMessage() {
        // Envia un mensaje a todos los clientes conectados
    	
    	String deviceToken = "c8KScdYeTZSfx7k4uoDlRy:APA91bHkQEZBtMVjmqMfYy1e4dA6RAtpfioEQJDo8XtD-5KskbfyRDtrSDNPLjfzwWyNh1OMXmnryOfkayJyBPK_oMeh0X-B5wsaKoteBs6vArRD1f_p3khkv-D9T30-8NmL2rjUxu_v";
    	List<String> deviceTokens = new ArrayList<String>();
    	deviceTokens.add(deviceToken);
    	//firebaseMessagingOperations.sendMulticastNotification(fcm, deviceTokens);
    	firebaseMessagingOperationsService.sendMulticastNotification(deviceTokens);
    	
        return "Mensaje enviado";
    }
    
	
	@PostMapping("/send-message")
	public String sendMessage(
			@RequestBody MessageFromServer message) {
		MessageFromServer messageFromServer = new MessageFromServer(0, MessageType.CLIENT, message.getMessage(), "default-room",DataType.TEXT , 1, "ASD");
		socketIOServer.getBroadcastOperations().sendEvent(SocketEvents.ON_SEND_MESSAGE.value, messageFromServer);
		return "enviado";
	}

	@PostMapping("/join-chat/{chatId}/{idUser}")
	public String joinChat(

			@PathVariable String chatId,
			@PathVariable Integer idUser) {
		SocketIOClient client = findClientByUserId(idUser);
		if (client != null) {
			client.joinRoom(chatId);
			socketIOServer.getRoomOperations(chatId).sendEvent(SocketEvents.ON_SEND_MESSAGE.value, idUser + " Se ha unido");
			return "el usuario se ha unido correctamente";
		}else {

			return "error al unirse";
		}



	}

	@PostMapping("/leave-chat/{chat}/{idUser}")
	public String leaveChat(
			@PathVariable String chatId,
			@PathVariable Integer idUser) {
		SocketIOClient client = findClientByUserId(idUser);
		
		
		if(client != null) {
			client.leaveRoom(chatId);
			socketIOServer.getRoomOperations(chatId).sendEvent(SocketEvents.ON_SEND_MESSAGE.value, idUser + " Ha abandonado el chat");
			return "el user ha abandonado el chat";
		}else {
			return "error al abandonar el chat";
		}

	}

	private SocketIOClient findClientByUserId(Integer idUser) {
		SocketIOClient response = null;

		Collection<SocketIOClient> clients = socketIOServer.getAllClients();
		for (SocketIOClient client: clients) {
			Integer currentClientId = Integer.valueOf(client.get(SocketIOConfig.CLIENT_USER_ID_PARAM));
			if (currentClientId == idUser) {
				response = client;
				break;
			}
		}

		return response;


	}




}
