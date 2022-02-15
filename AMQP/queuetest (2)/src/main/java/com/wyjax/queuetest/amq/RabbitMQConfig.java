package com.wyjax.queuetest.amq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String exchangeName = "aa.order.fanout";
    public static final String queueName = "wyjax.queue";


    @RabbitListener(bindings = @QueueBinding(
            value = @org.springframework.amqp.rabbit.annotation.Queue(value = queueName),
            exchange = @Exchange(value = "aa"),
            key = "aa.bb.#"))
    public void good(String message) {
        System.out.println("receive message : " + message);
    }

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
    public void receive(String message) {
        System.out.println(message);
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}
