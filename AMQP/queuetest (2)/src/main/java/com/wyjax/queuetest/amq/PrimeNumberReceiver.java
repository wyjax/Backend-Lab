package com.wyjax.queuetest.amq;

import com.wyjax.queuetest.service.PrimeNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
public class PrimeNumberReceiver {
    private CountDownLatch latch = new CountDownLatch(1);
    private final PrimeNumberService primeNumberService;

    public void receive(String message) {
        Long number = Long.parseLong(message);
        primeNumberService.process(number);

        System.out.println("receive message : " + message);
        latch.countDown();
    }

    @RabbitListener(bindings = {})

    public CountDownLatch getLatch() {
        return latch;
    }
}
