package com.example.springbean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    - @Bean으로 빈을 등록할 경우에는 @Configuration으로 빈을 등록해준다.
    - @Configuration을 설정하면 어노테이션 환경구성을 돕는다. 하나 이상의 @Bean이 있다는 것을 알려준다.
 */
@Configuration
public class BeanSample {

    public BeanSample() {
        System.out.println("BeanSample");
    }

    // 이런식으로 @Bean을 등록하려면 class에 @Configuration이 있어야함
    @Bean
    public Person person() {
        return new Person();
    }
}

class Person {
    public Person() {

    }
}