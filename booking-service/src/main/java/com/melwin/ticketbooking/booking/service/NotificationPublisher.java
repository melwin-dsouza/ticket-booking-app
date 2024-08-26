package com.melwin.ticketbooking.booking.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.melwin.ticketbooking.booking.dto.NotificationDetails;


@Service
public class NotificationPublisher {

    @Value("${rabbitmq.routing.exchange}")
    private String exchange;

    @Value("${notification.routing.name}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public NotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(NotificationDetails request){
        System.out.println(String.format("Message sent -> %s",request));
        rabbitTemplate.convertAndSend(exchange,routingKey,request);
    }
}
