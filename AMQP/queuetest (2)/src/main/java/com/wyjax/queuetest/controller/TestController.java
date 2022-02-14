package com.wyjax.queuetest.controller;

import com.wyjax.queuetest.amq.PrimeNumberReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TestController {
    private final RabbitTemplate template;
    private final PrimeNumberReceiver primeNumberReceiver;

    @PostMapping("/api/queue")
    public void sendMessage(String message) {
        template.convertAndSend("test-exchange", "um.jung.#", message);
    }

    @GetMapping("/api/queue")
    public void getMessage() {
        primeNumberReceiver.getLatch().countDown();
    }
}
