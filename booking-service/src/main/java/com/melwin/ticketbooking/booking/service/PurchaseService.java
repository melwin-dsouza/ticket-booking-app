package com.melwin.ticketbooking.booking.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melwin.ticketbooking.booking.client.EventClient;
import com.melwin.ticketbooking.booking.converter.PurchaseConverter;
import com.melwin.ticketbooking.booking.converter.TicketConverter;
import com.melwin.ticketbooking.booking.dto.EventDTO;
import com.melwin.ticketbooking.booking.dto.NotificationDetails;
import com.melwin.ticketbooking.booking.dto.PaymentRequest;
import com.melwin.ticketbooking.booking.dto.PaymentResponse;
import com.melwin.ticketbooking.booking.dto.PaymentStatus;
import com.melwin.ticketbooking.booking.dto.PurchaseDTO;
import com.melwin.ticketbooking.booking.dto.PurchaseRequest;
import com.melwin.ticketbooking.booking.entity.Purchase;
import com.melwin.ticketbooking.booking.entity.PurchaseStatus;
import com.melwin.ticketbooking.booking.entity.Ticket;
import com.melwin.ticketbooking.booking.entity.TicketStatus;
import com.melwin.ticketbooking.booking.entity.User;
import com.melwin.ticketbooking.booking.exception.ApiRequestException;
import com.melwin.ticketbooking.booking.exception.BookingServiceException;
import com.melwin.ticketbooking.booking.repository.PurchaseRepository;
import com.melwin.ticketbooking.booking.repository.TicketRepository;
import com.melwin.ticketbooking.booking.repository.UserRepository;

@Service
public class PurchaseService {

	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private EventClient eventClient;
	@Autowired
	PaymentRequestPublisher paymentPublisher;
	@Autowired
	UserRepository userRepository;
	@Autowired
	NotificationPublisher notificationPublisher;

	@Transactional
	public Long createPurchase(PurchaseRequest request) {

		// 1. Is event currently active?
		if (!eventClient.isEventActive(request.getEventId())) {
			throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Event is currently inactive.");
		}
		// 1. call Event service Rest template or use Eureka and feign client

		// 2. Check if the given quantity of tickets are available by running query
		Long availableTickets = ticketRepository.checkTicketAvailability(request.getEventId(), request.getType().toString());
		if (availableTickets < request.getQuantity()) {
			throw new ApiRequestException(HttpStatus.BAD_REQUEST,
					request.getQuantity() + " Tickets are unavailable. Available tickets: " + availableTickets);
		}


		// 4.create new purchase and set to in progress update purchase to inprogress
		Double ticketPrice = ticketRepository.getTicketAmount(request.getEventId(), request.getType().toString());

		User user = userRepository.findById(request.getUserId()).orElse(null);
		Purchase purchase = new Purchase();
		purchase.setEventId(request.getEventId());
		purchase.setUser(user);
		purchase.setStatus(PurchaseStatus.IN_PROGRESS);
		purchase.setAmount(ticketPrice * request.getQuantity());
		purchase = purchaseRepository.save(purchase);
		
		// 3. If tickets are available book first n tickets set status to hold
		ticketRepository.updateTicketStatus(TicketStatus.ON_HOLD.toString(), purchase.getId(),user.getId(), request.getEventId(), request.getType().toString(),
				request.getQuantity());
		// 4. call payment service using mq
		paymentPublisher.sendMessage(
				new PaymentRequest(purchase.getId(), user.getId(), ticketPrice * request.getQuantity(), null));
		return purchase.getId();
	}

	public PurchaseDTO getPurchase(Long id) {
		Purchase purchase = purchaseRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Purchase order Not Found"));
		return PurchaseConverter.toDTO(purchase);
	}

	@Transactional
	public void paymentCallback(PaymentResponse response) {
		Purchase purchase=purchaseRepository.findById(response.getPurchaseId()).orElse(null);
		if(Objects.nonNull(purchase)) {
			if(PaymentStatus.SUCCESS.equals(response.getStatus())) {
				purchase.setStatus(PurchaseStatus.BOOKED);
			}else {
				purchase.setStatus(PurchaseStatus.FAILED);
			}
			purchase = purchaseRepository.save(purchase);
			ticketRepository.updateTicketStatus(
					PaymentStatus.SUCCESS.equals(response.getStatus()) ? TicketStatus.BOOKED.toString() : TicketStatus.AVAILABLE.toString(),
					purchase.getId());
			EventDTO event=eventClient.getEventById(purchase.getEventId());
			notificationPublisher.sendMessage(createNotification(purchase, response, event));
		}
		
	}
	
	private NotificationDetails createNotification(Purchase purchase,PaymentResponse paymentResponse,EventDTO event) {
		return NotificationDetails.builder().userEmail(purchase.getUser().getEmail())
				.userName(purchase.getUser().getName())
				.userId(purchase.getUser().getId())
				.eventId(purchase.getEventId())
				.eventName(event.getName())
				.eventDate(event.getDate())
				.eventLocation(event.getLocation())
				.ticketType(purchase.getTickets().getFirst().getTicketType().toString())
				.ticketQty(purchase.getTickets().size())
				.paymentAmount(purchase.getAmount())
				.bookingStatus(paymentResponse.getStatus().toString()).build();			
		
		
	}

}
