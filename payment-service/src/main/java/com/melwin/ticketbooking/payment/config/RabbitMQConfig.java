package com.melwin.ticketbooking.payment.config;

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

    @Value("${payment.request.queue.name}")
    private String requestQueue;

    @Value("${payment.request.routing.name}")
    private String requestRoutingKey;
    
    @Value("${payment.response.queue.name}")
    private String responseQueue;

    @Value("${payment.response.routing.name}")
    private String responseRoutingKey;
        
    @Value("${rabbitmq.routing.exchange}")
    private String ticketExchange;

    
    @Bean
    Queue paymentRequestQueue(){
        return new Queue(requestQueue);
    }

    @Bean
    Queue paymentResponseQueue(){
        return new Queue(responseQueue);
    }
    
    @Bean
    TopicExchange exchange(){
        return new TopicExchange(ticketExchange);
    }

    @Bean
    Binding paymentRequestBinding(){
        return BindingBuilder.bind(paymentRequestQueue())
                .to(exchange())
                .with(requestRoutingKey);
    }
    
    @Bean
    Binding paymentResponseBinding(){
        return BindingBuilder.bind(paymentResponseQueue())
                .to(exchange())
                .with(responseRoutingKey);
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