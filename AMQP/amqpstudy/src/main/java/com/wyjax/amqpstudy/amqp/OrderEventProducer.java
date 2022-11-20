package com.wyjax.amqpstudy.amqp;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public void send(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.basicPublish("aa.order.fanout", "", null, message.getBytes(StandardCharsets.UTF_8));
            AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
            System.out.println("send end.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        rabbitTemplate.convertAndSend("aa.order.fanout", "", message);
    }
}
