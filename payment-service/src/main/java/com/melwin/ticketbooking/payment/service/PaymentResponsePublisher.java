package com.melwin.ticketbooking.payment.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.melwin.ticketbooking.payment.dto.PaymentResponse;

@Service
public class PaymentResponsePublisher {

	@Value("${rabbitmq.routing.exchange}")
	private String exchange;

	@Value("${payment.response.routing.name}")
	private String routingKey;

	private RabbitTemplate rabbitTemplate;

	public PaymentResponsePublisher(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessage(PaymentResponse response) {
		System.out.println(String.format("Message sent -> %s", response));
		rabbitTemplate.convertAndSend(exchange, routingKey, response);
	}
}
