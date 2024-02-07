package com.example.reto2_chat_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Reto2ChatServerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Reto2ChatServerApplication.class);
        application.setAdditionalProfiles("ssl");
        application.run(args);
	}

}
