package com.melwin.ticketbooking.notification.converter;

import org.springframework.beans.BeanUtils;

import com.melwin.ticketbooking.notification.dto.NotificationDetails;
import com.melwin.ticketbooking.notification.entity.Notification;

public class NotificationConverter {
	
	public static Notification toEntity(NotificationDetails details) {
		Notification notification=new Notification();
		BeanUtils.copyProperties(details, notification);
		return notification;
	}
	

}
