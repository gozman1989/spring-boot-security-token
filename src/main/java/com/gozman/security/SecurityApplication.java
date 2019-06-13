package com.gozman.security;

import com.gozman.security.token.TokenAuthentificationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;

//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		ApplicationContext a = SpringApplication.run(SecurityApplication.class, args);

	}

}
