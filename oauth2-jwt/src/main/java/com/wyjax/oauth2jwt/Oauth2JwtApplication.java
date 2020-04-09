package com.wyjax.oauth2jwt;

import com.wyjax.oauth2jwt.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class Oauth2JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2JwtApplication.class, args);
    }

}
