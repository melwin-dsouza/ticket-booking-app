package com.melwin.ticketbooking.booking.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.melwin.ticketbooking.booking.dto.PaymentRequest;


@Service
public class PaymentRequestPublisher {

    @Value("${rabbitmq.routing.exchange}")
    private String exchange;

    @Value("${payment.request.routing.name}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public PaymentRequestPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(PaymentRequest request){
        System.out.println(String.format("Message sent -> %s",request));
        rabbitTemplate.convertAndSend(exchange,routingKey,request);
    }
}
