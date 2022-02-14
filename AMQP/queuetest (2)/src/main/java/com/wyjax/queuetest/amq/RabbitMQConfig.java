package com.wyjax.queuetest.amq;

import org.springframework.amqp.core.Queue;
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
    public static final String topicExchangeName = "wyjax-exchange";
    public static final String queueName = "wyjax.queue";

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(topicExchangeName);
//    }

//    @Bean
//    public Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
//    }

    @RabbitListener(bindings = @QueueBinding(
            value = @org.springframework.amqp.rabbit.annotation.Queue(value = queueName),
            exchange = @Exchange(value = "aa"),
            key = "aa.bb.#"))
    public void good(String message) {
        System.out.println("receive message : " + message);
    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(PrimeNumberReceiver receiver, Jackson2JsonMessageConverter converter) {
//        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(receiver, "receive");
//        listenerAdapter.setMessageConverter(converter);
//        return listenerAdapter;
//    }

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

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory factory, MessageListenerAdapter adapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(factory);
//        container.setQueueNames(queueName);
//        container.setMessageListener(adapter);
//        return container;
//    }
}
