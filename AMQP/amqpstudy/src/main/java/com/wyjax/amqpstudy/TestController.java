package com.wyjax.amqpstudy;

import com.wyjax.amqpstudy.amqp.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final OrderEventProducer orderEventProducer;

    @PostMapping("/api/test")
    public void test(String message) {
        orderEventProducer.send(message);
    }
}
