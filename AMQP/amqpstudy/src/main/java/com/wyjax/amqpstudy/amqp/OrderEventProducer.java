package com.wyjax.amqpstudy.amqp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend("aa.order.fanout", "", message);
        System.out.println("send message : " + message);
    }
}
