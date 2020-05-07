package com.example.springbean;

import org.springframework.stereotype.Component;


// Component는 개발자가 직접 만든 class를 @Bean으로 등록한다.
// @ComponentScan에 의해서 찾아짐
@Component
public class ConfigurationBean {
    public ConfigurationBean() {
        System.out.println("ConfigurationBean");
    }
}