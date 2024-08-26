package com.melwin.ticketbooking.payment.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melwin.ticketbooking.payment.dto.PaymentRequest;



@Service
public class PaymentRequestConsumer {
	
	@Autowired
	PaymentService service;

    @RabbitListener(queues = {"${payment.request.queue.name}"})
    public void consume(PaymentRequest request){
        System.out.println(String.format("Received message -> %s", request));
       	service.completePayment(request);
    }
}
