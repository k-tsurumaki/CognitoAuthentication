package com.example.CognitoAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CognitoAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CognitoAuthenticationApplication.class, args);
	}
}
