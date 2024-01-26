package com.example.reto2_chat_server.controllers;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/sockets")
public class SocketController {
	private final SocketIOServer socketIOServer;

	@Autowired
	public SocketController(SocketIOServer socketIOServer) {
		this.socketIOServer = socketIOServer;
	}
	
	@PostMapping("/send-message")
	public String sendMessage(
			@RequestBody MessageFromServer message) {
		//TODO modificar el constructor
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
			//TODO notificar al user que se ha unido
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
		System.out.println(idUser);

		Collection<SocketIOClient> clients = socketIOServer.getAllClients();
		for (SocketIOClient client: clients) {
			Integer currentClientId = Integer.valueOf(client.get(SocketIOConfig.CLIENT_USER_ID_PARAM));
			System.out.println(currentClientId);
			if (currentClientId == idUser) {
				response = client;
				break;
			}
		}

		return response;


	}




}
