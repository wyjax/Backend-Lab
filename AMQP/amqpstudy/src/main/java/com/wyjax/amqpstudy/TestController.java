package com.wyjax.amqpstudy;

import com.wyjax.amqpstudy.amqp.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final MessageProducer messageProducer;

    @PostMapping("/api/test")
    public void test(String message) {
        messageProducer.send(message);
    }
}
