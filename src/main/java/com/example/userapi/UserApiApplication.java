package com.example.userapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the User API application.
 */
@SpringBootApplication
public class UserApiApplication {

	/**
	 * The main method which serves as the entry point for the application.
	 *
	 * @param args Command line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
	}

}
