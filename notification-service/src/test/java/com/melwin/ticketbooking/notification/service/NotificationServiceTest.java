package com.melwin.ticketbooking.notification.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.melwin.ticketbooking.notification.dto.NotificationDetails;
import com.melwin.ticketbooking.notification.entity.Notification;
import com.melwin.ticketbooking.notification.repository.NotificationRepository;

import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.SendEmailRequest;
import software.amazon.awssdk.services.sesv2.model.SendEmailResponse;



@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

	@Mock
	private NotificationRepository notificationRepository;

	@Mock
	private SesV2Client sesClient;
	@InjectMocks
	private NotificationService notificationService;

	Notification notification;
	NotificationDetails dto;

	@BeforeEach
	public void setup() {
		notification = new Notification();
		notification.setMessage("test");
		notification.setEventName("event");
		dto = new NotificationDetails();
		dto.setEventName("event");
		dto.setBookingStatus("SUCCESS");
	}

	@Test
	void notifyUser() {
		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
		SendEmailResponse value =SendEmailResponse.builder().build();
		when(sesClient.sendEmail(any(SendEmailRequest.class))).thenReturn(value);
		notificationService.notifyUser(dto);
		verify(notificationRepository, times(1)).save(any(Notification.class));
		verify(sesClient, times(1)).sendEmail(any(SendEmailRequest.class));
	}

	

}