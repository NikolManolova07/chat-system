package com.course_project_final.chat_system_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ChatSystemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatSystemApiApplication.class, args);
	}

}
