package com.melwin.ticketbooking.event.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.melwin.ticketbooking.event.dto.EventDetailsDTO;



@FeignClient(name="booking-service",url = "booking-service:8082/ticketbooking/ticket/")
public interface BookingClient {
	
	@GetMapping("{eventId}/details")
	List<EventDetailsDTO> getTicketAvailabilityDetails(@PathVariable("eventId") Long eventId);
	

}
