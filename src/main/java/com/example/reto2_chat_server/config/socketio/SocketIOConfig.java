package com.example.reto2_chat_server.config.socketio;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.*;
import com.example.reto2_chat_server.chat.service.ChatService;
import com.example.reto2_chat_server.chat.service.ChatServiceModel;
import com.example.reto2_chat_server.chat.service.MessageService;
import com.example.reto2_chat_server.model.message.DataType;
import com.example.reto2_chat_server.model.message.Message;
import com.example.reto2_chat_server.model.message.MessageFromClient;
import com.example.reto2_chat_server.model.message.MessageFromServer;
import com.example.reto2_chat_server.model.message.MessageSend;
import com.example.reto2_chat_server.model.message.MessageServiceModel;
import com.example.reto2_chat_server.model.message.MessageType;
import com.example.reto2_chat_server.security.configuration.JwtTokenUtil;
import com.example.reto2_chat_server.security.user.service.UserServiceModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.netty.handler.codec.http.HttpHeaders;
import jakarta.annotation.PreDestroy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;

@Configuration
public class SocketIOConfig {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private ChatService chatService;
	@Autowired
	private MessageService messageService;

	@Value("${socket-server.host}")
	private String host;

	@Value("${socket-server.port}")
	private Integer port;

	private SocketIOServer server;

	public final static String CLIENT_USER_NAME_PARAM = "email";
	public final static String CLIENT_USER_ID_PARAM = "id";
	public final static String AUTHORIZATION_HEADER = "Authorization";

	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		config.setHostname(host);
		config.setPort(port);
		config.setAllowHeaders("Authorization");
		config.setOrigin("http://10.5.7.37:8080");

		server = new SocketIOServer(config);

		server.addConnectListener(new MyConnectListener(server, jwtTokenUtil, chatService));
		server.addDisconnectListener(new MyDisconnectListener());
		server.addEventListener(SocketEvents.ON_MESSAGE_RECEIVED.value, MessageFromClient.class, onSendMessage());
		server.start();

		return server;

	}

	// Clase para gestionar la conexion
	private static class MyConnectListener implements ConnectListener {

		private SocketIOServer server;
		private JwtTokenUtil jwtTokenUtil;
		private ChatService chatService;

		MyConnectListener(SocketIOServer server, JwtTokenUtil jwtTokenUtil, ChatService chatService) {
			this.server = server;
			this.jwtTokenUtil = jwtTokenUtil;
			this.chatService = chatService;
		}

		@Override
		public void onConnect(SocketIOClient client) {
			// TODO Auto-generated method stub
			HttpHeaders headers = client.getHandshakeData().getHttpHeaders();

			if (headers.get(AUTHORIZATION_HEADER) == null) {

				client.disconnect();
			} else {
				loadClientData(headers, client);

				System.out.printf("Nuevo cliente conectado: %s . Clientes conectados ahora mismo: %d \n",
						client.getSessionId(), this.server.getAllClients().size());

			}

		}

		private void loadClientData(HttpHeaders headers, SocketIOClient client) {
			try {
				String authorization = headers.get(AUTHORIZATION_HEADER);
				String jwt = authorization.split(" ")[1];
				if (jwtTokenUtil.validateAccessToken(jwt)) {
					Integer userId = jwtTokenUtil.getUserId(jwt);
					String userEmail = jwtTokenUtil.getUserEmail(jwt);
					client.set(CLIENT_USER_ID_PARAM, userId.toString());
					client.set(CLIENT_USER_NAME_PARAM, userEmail);
					client.joinRoom("default-room");
					
					List<Integer> listOfChats = chatService.getChatsIdsByUserId(userId);
					for (Integer chat : listOfChats) {
						client.joinRoom("Group- " + chat);
					}

				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}

	// Clase para gestionar la desconexion
	private static class MyDisconnectListener implements DisconnectListener {

		@Override
		public void onDisconnect(SocketIOClient client) {
			// TODO Auto-generated method stub
			client.getNamespace().getAllClients().stream().forEach(data -> {
				notificateDisconnectToUsers(client);

			});

		}

		private void notificateDisconnectToUsers(SocketIOClient client) {
			// TODO actualizar campos segun el token
			String room = null;
			String message = "el usuario se ha desconectado";
			String authorIdS = client.get(CLIENT_USER_ID_PARAM);
			Integer authorId = Integer.valueOf(authorIdS);
			String authorName = client.get(CLIENT_USER_NAME_PARAM);

			MessageFromServer messageFromServer = new MessageFromServer(0, MessageType.CLIENT, message, room,
					DataType.TEXT, authorId, authorName);
			client.getNamespace().getBroadcastOperations().sendEvent(SocketEvents.ON_SEND_MESSAGE.value,
					messageFromServer);

		}

	}

	private DataListener<MessageFromClient> onSendMessage() {
		return (senderClient, data, ackowledge) -> {
			String authorIdS = senderClient.get(CLIENT_USER_ID_PARAM);
			Integer authorId = Integer.valueOf(authorIdS);
			String authorName = senderClient.get(CLIENT_USER_NAME_PARAM);

			System.out.printf("Mensaje recibido de (ID: %d, Email: %s). El mensaje es el siguiente: %s \n", authorId,
					authorName, data.toString());

			if (checkIfIsAllowedToSend(senderClient, data.getRoom())) {
				// TODO completar el constructor

				// TODO guargar en base de datos
				UserServiceModel userId = new UserServiceModel();
				userId.setId(authorId);
				long currentTimeMillis = System.currentTimeMillis();

				// Convert milliseconds to java.sql.Date
				Date currentDate = new Date(currentTimeMillis);

				MessageSend message = new MessageSend(0, DataType.TEXT, data.getMessage(), currentDate, userId,
						new ChatServiceModel(Integer.parseInt(
								data.getRoom().substring(data.getRoom().length() - 1, data.getRoom().length()))));
				Message insertMessage = messageService.insertMessage(message);
				System.out.println(insertMessage);
				MessageFromServer messageFromServer = new MessageFromServer(insertMessage.getId(), MessageType.CLIENT, data.getMessage(),
						data.getRoom(), DataType.TEXT, authorId, authorName);

				server.getRoomOperations(data.getRoom()).sendEvent(SocketEvents.ON_SEND_MESSAGE.value,
						messageFromServer);

			} else {
				// TODO falta manejar el no poder enviar el mensaje
			}

		};

	}

	private boolean checkIfIsAllowedToSend(SocketIOClient senderClient, String room) {
		// Comprueba si el usuario puede enviar mensajes a la room
		if (senderClient.getAllRooms().contains(room)) {
			return true;
		} else {
			return false;
		}

	}

	@PreDestroy
	public void stopSocketIOServer() {
		this.server.stop();
	}
}
