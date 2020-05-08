package com.example.springaop;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ComponentBean {

    public void prints() {
        System.out.println("good");
    }

    @Bean
    public String good() {
        return "hello world";
    }
}
