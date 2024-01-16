package com.example.reto2_chat_server.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.reto2_chat_server.config.socketio.SocketEvents;
import com.example.reto2_chat_server.config.socketio.SocketIOConfig;
import com.example.reto2_chat_server.model.MessageFromServer;

@RestController
@RequestMapping("/api/sockets")
public class SocketController {
	private final SocketIOServer socketIOServer;

	@Autowired
	public SocketController(SocketIOServer socketIOServer) {
		this.socketIOServer = socketIOServer;
	}

	@GetMapping("/chats")
	public String chats() {
		System.out.println("android");
		return "enviado";
	}
	
	@GetMapping("/send-message")
	public String sendMessage() {
		//TODO modificar el constructor
		MessageFromServer messageFromServer = new MessageFromServer();
		socketIOServer.getBroadcastOperations().sendEvent(SocketEvents.ON_SEND_MESSAGE.value, messageFromServer);
		return "enviado";
	}

	@PostMapping("/join-chat/{chat}/{idUser}")
	public String joinChat(

			//TODO ¿deberia ser el id del chat y del user? el metodo joinroom acepta un string por parametro
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
