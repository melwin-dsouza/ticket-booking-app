package com.melwin.ticketbooking.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.melwin.ticketbooking.booking.client.EventClient;
import com.melwin.ticketbooking.booking.dto.EventDTO;
import com.melwin.ticketbooking.booking.dto.NotificationDetails;
import com.melwin.ticketbooking.booking.dto.PaymentRequest;
import com.melwin.ticketbooking.booking.dto.PaymentResponse;
import com.melwin.ticketbooking.booking.dto.PaymentStatus;
import com.melwin.ticketbooking.booking.dto.PurchaseDTO;
import com.melwin.ticketbooking.booking.dto.PurchaseRequest;
import com.melwin.ticketbooking.booking.dto.UserDTO;
import com.melwin.ticketbooking.booking.entity.Purchase;
import com.melwin.ticketbooking.booking.entity.PurchaseStatus;
import com.melwin.ticketbooking.booking.entity.Ticket;
import com.melwin.ticketbooking.booking.entity.TicketType;
import com.melwin.ticketbooking.booking.entity.User;
import com.melwin.ticketbooking.booking.repository.PurchaseRepository;
import com.melwin.ticketbooking.booking.repository.TicketRepository;
import com.melwin.ticketbooking.booking.repository.UserRepository;



@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

	@Mock
	private PurchaseRepository purchaseRepository;
	@Mock
	private TicketRepository ticketRepository;
	@Mock
	private EventClient eventClient;
	@Mock
	PaymentRequestPublisher paymentPublisher;
	@Mock
	UserRepository userRepository;
	@Mock
	NotificationPublisher notificationPublisher;

	@InjectMocks
	private PurchaseService purchaseService;
	
	User user;
	Purchase purchase;
	Ticket ticket;
	UserDTO dto;

	@BeforeEach
	public void setup() {
		user = new User();
		user.setName("testName");
		ticket=new Ticket();
		ticket.setTicketType(TicketType.NORMAL);
		purchase=new Purchase();
		purchase.setEventId(1L);
		purchase.setUser(user);
		purchase.setTickets(List.of(ticket));
		purchase.setStatus(PurchaseStatus.BOOKED);
		dto = new UserDTO(1L, "testName", "97124845", "test@gmail.com", null);
	}

	@Test
	void createPurchase() {
		when(eventClient.isEventActive(anyLong())).thenReturn(true);
		when(ticketRepository.checkTicketAvailability(anyLong(),anyString())).thenReturn(5L);
		when(ticketRepository.getTicketAmount(anyLong(),anyString())).thenReturn(200.0);

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);
		when(ticketRepository.updateTicketStatus(anyString(), any(), any(), anyLong(), anyString(), anyInt())).thenReturn(2);
		doNothing().when(paymentPublisher).sendMessage(any(PaymentRequest.class));
		Long response = purchaseService.createPurchase(new PurchaseRequest(1L, 1L, 3, TicketType.NORMAL));
		assertEquals(response, null);
		verify(ticketRepository, times(1)).updateTicketStatus(anyString(), any(), any(), anyLong(), anyString(), anyInt());
	}

	@Test
	void getPurchase() {
		when(purchaseRepository.findById(anyLong())).thenReturn(Optional.of(purchase));
		PurchaseDTO response = purchaseService.getPurchase(1L);
		assertEquals(response.getEventId(), 1);
		verify(purchaseRepository, times(1)).findById(anyLong());
	}

	@Test
	void paymentCallback() {
		when(purchaseRepository.findById(anyLong())).thenReturn(Optional.of(purchase));

		when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);
		when(ticketRepository.updateTicketStatus(anyString(), any())).thenReturn(2);
		when(eventClient.getEventById(anyLong())).thenReturn(new EventDTO());
		doNothing().when(notificationPublisher).sendMessage(any(NotificationDetails.class));
		purchaseService.paymentCallback(new PaymentResponse(1L, PaymentStatus.SUCCESS));
		verify(purchaseRepository, times(1)).save(any(Purchase.class));
	}

	
}