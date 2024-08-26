package com.melwin.ticketbooking.notification.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melwin.ticketbooking.notification.dto.NotificationDetails;



@Service
public class NotificationConsumer {
	
	@Autowired
	NotificationService service;

    @RabbitListener(queues = {"${notification.queue.name}"})
    public void consume(NotificationDetails response){
        System.out.println(String.format("Received message -> %s", response));
       	service.notifyUser(response);
    }
}
