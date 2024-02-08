package com.example.reto2_chat_server.config.firebase;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;


@Configuration
public class FirebaseConfiguration {

	
	

		// https://www.baeldung.com/spring-fcm
		// https://firebase.google.com/docs/admin/setup?hl=es-419
		
		@Value("${custom.firebase.credentials.path}")
		private Resource firebaseCredentials;
		
		//cargar las credenciales
		@Bean
		GoogleCredentials googleCredentials() throws IOException {
			if (FirebaseApp.getApps().isEmpty()) {
				// para no usar variable de entorno
				try {
		            if (firebaseCredentials != null) {
		                return GoogleCredentials.fromStream(firebaseCredentials.getInputStream());
		                
		            } else {
		                throw new IllegalStateException("Firebase credentials not provided.");
		            }
		        } catch (IOException exception) {
		            return GoogleCredentials.getApplicationDefault();
		        }
			} else {
				// Use standard credentials chain. Useful when running inside GKE
		        return GoogleCredentials.getApplicationDefault();
			}
		}
		
	
		@Bean
		FirebaseApp firebaseApp(GoogleCredentials credentials) {
		    FirebaseOptions options = FirebaseOptions.builder()
		      .setCredentials(credentials)
		      .build();

		    return FirebaseApp.initializeApp(options);
		}
		
		// Firebase Messaging (FCM)
		@Bean
		FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
		    return FirebaseMessaging.getInstance(firebaseApp);
		}
		
		//crear bbdd

		
}
