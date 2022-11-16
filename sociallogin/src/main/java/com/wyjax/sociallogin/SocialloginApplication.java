package com.wyjax.sociallogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class SocialloginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialloginApplication.class, args);
	}

}
