package com.example.reto2_chat_server.service.firebase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

@Service
public class FirebaseMessagingOperations implements FirebaseMessagingOperationsService {
	
	
	private final FirebaseMessaging fcm;
	@Autowired
	public FirebaseMessagingOperations(FirebaseMessaging fcm) {
    	this.fcm = fcm;
    }
	
	@Override
    public void sendMulticastNotification(List deviceTokens) {
    	
    	// mandamos notificacion a los clientes de la lista. ESTO ESTA HARDCODEADO!
    	
    	
    	// para un usuario
    	/*
    	String deviceToken = "";
    	Message msg = Message.builder()
    			  .setToken(deviceToken)
    			  .putData("body", "some data")
    			  .build();
    	*/
    	// para muchos usuarios
    
		if (deviceTokens.isEmpty()) {
            System.out.println("No hay tokens de dispositivos para enviar notificaciones.");
            return;
        }

    	Notification notification = Notification.builder()
    		.setTitle("tituloaa")
    		.setBody("Cuerpoaaaa")
    		.build();
    	
    	@SuppressWarnings("unchecked")
		MulticastMessage msg = MulticastMessage.builder()
			.addAllTokens(deviceTokens)
			.setNotification(notification)
			.putData("body", "some data")
			.build();
    	try {
			fcm.sendMulticast(msg);
			//System.out.println("enviado");
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    System.err.println("Error al enviar la notificación: " + e.getMessage());
		}
    	// The returned BatchResponse contains the generated message identifiers and any errors associated with the delivery for a given client.
    	// BatchResponse response = fcm.sendMulticast(msg);
	}

}