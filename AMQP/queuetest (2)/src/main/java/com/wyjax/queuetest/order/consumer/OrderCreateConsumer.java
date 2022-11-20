package com.wyjax.queuetest.order.consumer;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OrderCreateConsumer {
    private static final String exchangeName = "aa.order.fanout";
    private static final String queueName = "wyjax.queue";

    private final RabbitTemplate rabbitTemplate;

    @Bean
    public Declarables orderDeclarable() {
        Queue queue = new Queue(queueName);
        FanoutExchange exchange = new FanoutExchange(exchangeName);

        return new Declarables(queue,
                exchange,
                BindingBuilder.bind(queue).to(exchange));
    }

    @RabbitListener(bindings = @QueueBinding(value = @org.springframework.amqp.rabbit.annotation.Queue(queueName),
            exchange = @Exchange(value = exchangeName, type = ExchangeTypes.FANOUT)))
    public void receive(Message message, Channel channel) throws IOException {
        System.out.println("receive message : " + message.getBody());
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, true);
    }
}
