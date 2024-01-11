package com.example.reto2_chat_server.config.socketio;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.*;
import com.example.reto2_chat_server.model.MessageFromClient;
import com.example.reto2_chat_server.model.MessageFromServer;

import io.netty.handler.codec.http.HttpHeaders;
import jakarta.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

	@Value("${socket-server.host}")
	private String host;

	@Value("${socket-server.port}")
	private Integer port;

	private SocketIOServer server;

	public final static String CLIENT_USER_NAME_PARAM = "authorname";
	public final static String CLIENT_USER_ID_PARAM = "authorid";
	public final static String AUTHORIZATION_HEADER = "Authorization";

	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		config.setHostname(host);
		config.setPort(port);
		config.setAllowHeaders("Authorization");
		config.setOrigin("http://localhost:8080");

		server = new SocketIOServer(config);

		server.addConnectListener(new MyConnectListener(server));
		server.addDisconnectListener(new MyDisconnectListener());
		server.addEventListener(SocketEvents.ON_MESSAGE_RECEIVED.value, MessageFromClient.class, onSendMessage());
		server.start();

		return server;

	}

	//Clase para gestionar la conexion
	private static class MyConnectListener implements ConnectListener{

		private SocketIOServer server;

		MyConnectListener(SocketIOServer server) {
			this.server = server;
		}

		@Override
		public void onConnect(SocketIOClient client) {
			// TODO Auto-generated method stub
			HttpHeaders headers = client.getHandshakeData().getHttpHeaders();

			if (headers.get(AUTHORIZATION_HEADER)== null) {
				client.disconnect();
			}else {
				loadClientData(headers,client);
				System.out.printf("Nuevo cliente conectado: %s . Clientes conectados ahora mismo: %d \n", client.getSessionId(), this.server.getAllClients().size());

			}

		}
		private void loadClientData(HttpHeaders headers, SocketIOClient client) {
			try {
				String authorization = headers.get(AUTHORIZATION_HEADER);
				String jwt = authorization.split(" ")[1];
				// TODO FALTA VALIDAR EL TOKEN Y OBTENER LOS DATOS
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}

	//Clase para gestionar la desconexion
	private static class MyDisconnectListener implements DisconnectListener{

		@Override
		public void onDisconnect(SocketIOClient client) {
			// TODO Auto-generated method stub
			client.getNamespace().getAllClients().stream().forEach(data ->{
				notificateDisconnectToUsers(client);
				System.out.println("user disconnected");

			});

		}
		private void notificateDisconnectToUsers(SocketIOClient client) {
			//TODO actualizar campos segun el token
			System.out.println("user disconnected");

			/*
			String room = null;
			String message = "el usuario se ha desconectado salido";
			String authorIdS = client.get(CLIENT_USER_ID_PARAM);
			Integer authorId = Integer.valueOf(authorIdS);
			String authorName = client.get(CLIENT_USER_NAME_PARAM);

			MessageFromServer messageFromServer = new MessageFromServer();
			client.getNamespace().getBroadcastOperations().sendEvent(SocketEvents.ON_SEND_MESSAGE.value, messageFromServer);
			*/
		}



	}
	private DataListener<MessageFromClient> onSendMessage() {
		return (senderClient, data ,ackowledge) -> {

			String authorIdS = senderClient.get(CLIENT_USER_ID_PARAM);
			Integer authorId = 1;
			String authorName = senderClient.get(CLIENT_USER_NAME_PARAM);


			if(checkIfIsAllowedToSend(senderClient, data.getRoom())) {
				//TODO completar el constructor
				MessageFromServer messageFromServer = new MessageFromServer();
				server.getRoomOperations(data.getRoom()).sendEvent(SocketEvents.ON_SEND_MESSAGE.value, messageFromServer);


			}else {
				//TODO falta manejar el no poder enviar el mensaje
			}

		};


	}
	private boolean checkIfIsAllowedToSend(SocketIOClient senderClient, String room) {
		//Comprueba si el usuario puede enviar mensajes a la room
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
