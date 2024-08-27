package com.melwin.ticketbooking.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.melwin.ticketbooking.booking.dto.EventDTO;

@FeignClient(name="event-service",url = "event-service:8081/ticketbooking/")
public interface EventClient {
	
	@GetMapping("/events/{eventId}/isActive")
	boolean isEventActive(@PathVariable("eventId") Long eventId);
	
	@GetMapping("/events/{eventId}/isValid")
	boolean isEventValid(@PathVariable("eventId") Long eventId);
	
	@GetMapping("/events/{eventId}")
	EventDTO getEventById(@PathVariable("eventId") Long eventId);

}
