package com.example.reto2_chat_server.config.socketio;
import java.text.SimpleDateFormat;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.*;
import com.example.reto2_chat_server.chat.controller.UsersFromChatsPostRequest;
import com.example.reto2_chat_server.chat.repository.Chat;
import com.example.reto2_chat_server.chat.service.ChatService;
import com.example.reto2_chat_server.chat.service.ChatServiceModel;
import com.example.reto2_chat_server.chat.service.DeleteChat;
import com.example.reto2_chat_server.chat.service.MessageService;
import com.example.reto2_chat_server.model.CrateChat;
import com.example.reto2_chat_server.model.message.DataType;
import com.example.reto2_chat_server.model.message.Message;
import com.example.reto2_chat_server.model.message.MessageFromClient;
import com.example.reto2_chat_server.model.message.MessageFromServer;
import com.example.reto2_chat_server.model.message.MessagePostRequestToSender;
import com.example.reto2_chat_server.model.message.MessageSend;
import com.example.reto2_chat_server.model.message.MessageType;
import com.example.reto2_chat_server.security.configuration.JwtTokenUtil;
import com.example.reto2_chat_server.security.user.service.UserServiceModel;
import io.netty.handler.codec.http.HttpHeaders;
import jakarta.annotation.PreDestroy;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;


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

	@Value("${server.ssl.key-store-password}")
	private String keyStorePassword;

	@Value("${server.ssl.key-store}")
	private Resource keyStoreFile;

	public final static String CLIENT_USER_NAME_PARAM = "email";
	public final static String CLIENT_USER_ID_PARAM = "id";
	public final static String AUTHORIZATION_HEADER = "Authorization";

	@Bean
	public SocketIOServer socketIOServer() throws IOException {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		config.setHostname(host);
		config.setPort(port);
		config.setAllowHeaders("Authorization");
		config.setOrigin("https://10.5.7.15:443");
		config.setMaxFramePayloadLength(2621440);
		config.setMaxHttpContentLength(2621440);
		config.setKeyStorePassword(keyStorePassword);
		InputStream stream = keyStoreFile.getInputStream();
		config.setKeyStore(stream);


		server = new SocketIOServer(config);
		server.addConnectListener(new MyConnectListener(server, jwtTokenUtil, chatService));
		server.addDisconnectListener(new MyDisconnectListener());
		server.addEventListener(SocketEvents.ON_MESSAGE_RECEIVED.value, MessageFromClient.class, onSendMessage());
		server.addEventListener(SocketEvents.ON_ADD_USER_CHAT_SEND.value, UsersFromChatsPostRequest.class, onAddUser());
		server.addEventListener(SocketEvents.ON_DELETE_USER_CHAT_SEND.value, UsersFromChatsPostRequest.class, onDeleteUser());
		server.addEventListener(SocketEvents.ON_CREATE_CHAT_SEND.value, CrateChat.class, createChat());
		server.addEventListener(SocketEvents.ON_DELETE_CHAT_SEND.value, DeleteChat.class, deleteChat());
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

			System.out.printf("Mensaje recibido de (ID: %d, Email: %s). El mensaje es el siguiente: %s del tipo " +data.getType() + " \n", authorId,
					authorName, data.toString());

			if (checkIfIsAllowedToSend(senderClient, data.getRoom())) {
				UserServiceModel userId = new UserServiceModel();
				userId.setId(authorId);
				long currentTimeMillis = System.currentTimeMillis();

				// Convert milliseconds to java.sql.Date
				Date currentDate = new Date(currentTimeMillis);

				MessageSend message = new MessageSend(0, data.getType(), data.getMessage(), currentDate,currentDate, userId,
						new ChatServiceModel(Integer.parseInt(
								data.getRoom().substring(data.getRoom().lastIndexOf("Group- ") + "Group- ".length()))));
				if(data.getType() == DataType.IMAGE) {
					message.setContent(safeImage(data.getMessage(), authorName));
				}else if(data.getType() == DataType.FILE) {
					message.setContent(safeFile(data.getMessage(), authorName));
				}

				Message insertMessage = messageService.insertMessage(message);
				System.out.println(insertMessage.toString());
				MessageFromServer messageFromServer = new MessageFromServer(insertMessage.getId(), MessageType.CLIENT, data.getMessage(),
						data.getRoom(), data.getType(), authorId, authorName);

				MessagePostRequestToSender responseTosender = new MessagePostRequestToSender(insertMessage.getId(), data.getIdRoom());

				senderClient.sendEvent(SocketEvents.ON_SEND_ID_MESSAGE.value, responseTosender);


				server.getRoomOperations(data.getRoom()).sendEvent(SocketEvents.ON_SEND_MESSAGE.value, senderClient, messageFromServer);


			} else {
				System.out.println("No se pudo enviar el mensaje");
			}

		};

	}

	public String safeFile(String message, String authorName) {
        try {
            String currentDate = getCurrentDate();
            String extensionArchivo = detectFileType(message);
            String fileName = authorName + "_" + currentDate + extensionArchivo;
            String outputFile = "src/main/resources/static/files/" + fileName;
            saveToFile(message, outputFile);
            return outputFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        return dateFormat.format(new java.util.Date());
    }

    private void saveToFile(String base64Content, String outputFile) throws IOException {
        byte[] decodedFile = Base64.getDecoder().decode(base64Content.getBytes());
        Path destinationFile = Paths.get(outputFile);
        Files.write(destinationFile, decodedFile);
    }
	
	private String detectFileType(String base64Content) {
	    HashMap<String, String> signatures = new HashMap<String, String>();
	    signatures.put("JVBERi0", ".pdf");
	    signatures.put("UEsDBBQ", ".docx"); // Signature for Word documents
	    signatures.put("504b0304", ".xlsx"); // Signature for Excel documents
	    signatures.put("D0CF11E0", ".xls"); // Signature for older Excel documents
	    signatures.put("25504446", ".pdf"); // Signature for PDF documents
	    // You can add more signatures for other file types here

	    for (Map.Entry<String, String> entry : signatures.entrySet()) {
	        String key = entry.getKey();
	        if (base64Content.startsWith(key)) {
	            return entry.getValue();
	        }
	    }
	    // If no match is found, return a default extension (you can customize this as needed)
	    return ".bin";
	}


	private String safeImage(String message, String authorName) {

		try {// TODO Auto-generated method stub
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	        String currentDate = dateFormat.format(new java.util.Date());
	        
			String extensionArchivo = decetMineType(message);
			String fileName = authorName + "_" + currentDate;
			String outputFile = "src/main/resources/static/images/" + fileName;
			byte[] decodedImg = Base64.getDecoder().decode(message.getBytes(StandardCharsets.UTF_8));
			Path destinationFile = Paths.get(outputFile);
			Files.write(destinationFile, decodedImg);
			return outputFile;

		} catch (IOException e) {
			e.printStackTrace();	
			return null;
		}        
	}


	private String  decetMineType(String base64Content) {
		HashMap<String, String> signatures = new HashMap<String, String>();
		signatures.put("JVBERi0", ".pdf");
		signatures.put("R0lGODdh", ".gif");
		signatures.put("R0lGODlh", ".gif");
		signatures.put("iVBORw0KGgo", ".png");
		signatures.put("/9j/", ".jpg");
		String response = "";

		for (Map.Entry<String, String> entry : signatures.entrySet()) {
			String key = entry.getKey();
			if(base64Content.indexOf(key) == 0) {
				response = entry.getValue();
			}
		}

		return response;		
	}


	private DataListener<UsersFromChatsPostRequest> onAddUser() {
		return (senderClient, data, ackowledge) -> {
			String authorIdS1 = senderClient.get(CLIENT_USER_ID_PARAM);
			Integer authorId1 = Integer.valueOf(authorIdS1);
			System.out.println(data.getUserId());
			System.out.println(authorId1);
			List<UsersFromChatsPostRequest> users = new ArrayList<UsersFromChatsPostRequest>();
			users.add(new UsersFromChatsPostRequest(data.getUserId(), data.getChatId(), data.isAdmin()));
			chatService.addUsersToChat(data.getChatId(), users, authorId1, true);
			
			for (SocketIOClient user : server.getAllClients()) {
				String authorIdS = user.get(CLIENT_USER_ID_PARAM);
				Integer authorId = Integer.valueOf(authorIdS);
				if(data.getUserId() == authorId) {	
					
					user.joinRoom("Group- " + data.getChatId());
					ChatServiceModel response = chatService.getChatsById(data.getChatId());
					user.sendEvent(SocketEvents.ON_ADD_USER_CHAT_RECIVE.value, response);
				}
			}
		}; 

	}
	
	private DataListener<DeleteChat> deleteChat() {
		return (senderClient, data, ackowledge) -> {
			String authorIdS1 = senderClient.get(CLIENT_USER_ID_PARAM);
			Integer authorId1 = Integer.valueOf(authorIdS1);
			ResponseEntity<?> response = chatService.deleteChatById(data.getChatId(), authorId1);
			if(response.getStatusCode() == HttpStatus.NO_CONTENT) {
				System.out.println("a");
				server.getRoomOperations("Group- " + data.getChatId()).sendEvent(SocketEvents.ON_DELETE_CHAT_RECIVE.value, senderClient, true);
				for (SocketIOClient user : server.getAllClients()) {
					user.leaveRoom("Group- " + data.getChatId());
				}
			}
			
		};
	}
	
	private DataListener<CrateChat> createChat() {
		return (senderClient, data, ackowledge) -> {
			Chat chat = new Chat(data.isaIsPublic(), data.getName());
			ResponseEntity<?> response = chatService.createChat(chat, data.getUserId());
			
			if (response.hasBody() && response.getBody() instanceof ChatServiceModel) {
				ChatServiceModel chatServiceModel = (ChatServiceModel) response.getBody();
				UsersFromChatsPostRequest creatorUserRequest = new UsersFromChatsPostRequest(data.getUserId(), chatServiceModel.getId(), true);
				List<UsersFromChatsPostRequest> listRequest = new ArrayList<UsersFromChatsPostRequest>();
				listRequest.add(creatorUserRequest);

				ResponseEntity<?> addUserResponse = chatService.addUsersToChat(chatServiceModel.getId(), listRequest, data.getUserId(), false);
				
				
				senderClient.joinRoom("Group- " + chatServiceModel.getId());
				
				ChatServiceModel chatNew = chatService.getChatsById(chatServiceModel.getId());
				System.out.println(data.getRoomChatId());
				chatNew.setIdRoom(data.getRoomChatId());
				chatNew.setUpdatedAt(null);
				chatNew.setCreatedAt(null);
				chatNew.setPublic(data.isaIsPublic());
				senderClient.sendEvent(SocketEvents.ON_CREATE_CHAT_RECIVE.value, chatNew);
				 
			}
		};
	}

	private DataListener<UsersFromChatsPostRequest> onDeleteUser() {
		return (senderClient, data, ackowledge) -> {
			String authorIdS1 = senderClient.get(CLIENT_USER_ID_PARAM);
			Integer authorId1 = Integer.valueOf(authorIdS1);
			
			List<UsersFromChatsPostRequest> users = new ArrayList<UsersFromChatsPostRequest>();
			users.add(new UsersFromChatsPostRequest(data.getUserId(), data.getChatId(), data.isAdmin()));
			chatService.removeUsersFromChat(data.getChatId(), users, authorId1);

			UsersFromChatsPostRequest userDeleted = new UsersFromChatsPostRequest(
					data.getUserId(),
					data.getChatId(),
					false
					);	
			
			server.getRoomOperations("Group- " + data.getChatId()).sendEvent(SocketEvents.ON_DELETE_USER_CHAT_RECIVE.value, userDeleted);
			for (SocketIOClient user : server.getAllClients()) {
				String authorIdS = user.get(CLIENT_USER_ID_PARAM);
				Integer authorId = Integer.valueOf(authorIdS);
				if(data.getUserId() == authorId) {	
					user.leaveRoom("Group- " + data.getChatId());
					break;
				}
			}	
			server.getRoomOperations("Group- " + data.getChatId()).sendEvent(SocketEvents.ON_DELETE_USER_CHAT_RECIVE.value, userDeleted);
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
