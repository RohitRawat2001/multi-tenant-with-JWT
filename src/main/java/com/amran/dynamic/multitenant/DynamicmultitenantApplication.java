package com.amran.dynamic.multitenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DynamicmultitenantApplication {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "securePassword123";  // Replace with the actual password
		String hashedPassword = encoder.encode(rawPassword);
		System.out.println("BCrypt Hash: " + hashedPassword);
		SpringApplication.run(DynamicmultitenantApplication.class, args);
	}

}
