package com.melwin.ticketbooking.booking.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melwin.ticketbooking.booking.dto.PaymentResponse;



@Service
public class PaymentResponseConsumer {
	
	@Autowired
	PurchaseService service;

    @RabbitListener(queues = {"${payment.response.queue.name}"})
    public void consume(PaymentResponse response){
        System.out.println(String.format("Received message -> %s", response));
       	service.paymentCallback(response);
    }
}
