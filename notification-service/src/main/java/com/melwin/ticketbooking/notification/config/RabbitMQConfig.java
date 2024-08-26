package com.melwin.ticketbooking.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    
    @Value("${notification.queue.name}")
    private String notificationQueue;

    @Value("${notification.exchange.name}")
    private String notificationExchange;
    
    @Value("${notification.routing.key}")
    private String routingKey;

    

    @Bean
    Queue notificationQueue(){
        return new Queue(notificationQueue);
    }
    
    @Bean
    TopicExchange notificationExchange(){
        return new TopicExchange(notificationExchange);
    }

    @Bean
    Binding notificationBinding(){
        return BindingBuilder.bind(notificationQueue())
                .to(notificationExchange())
                .with(routingKey);
    }

    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}