package com.melwin.ticketbooking.event.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.melwin.ticketbooking.event.client.BookingClient;
import com.melwin.ticketbooking.event.converter.EventConverter;
import com.melwin.ticketbooking.event.dto.EventDTO;
import com.melwin.ticketbooking.event.dto.EventDetailsDTO;
import com.melwin.ticketbooking.event.entity.Event;
import com.melwin.ticketbooking.event.entity.EventStatus;
import com.melwin.ticketbooking.event.exception.ApiRequestException;
import com.melwin.ticketbooking.event.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private BookingClient bookingClient;

	public EventDTO createEvent(EventDTO eventDto) {
		Event event = EventConverter.toEntity(eventDto, new Event());
		event.setStatus(EventStatus.ACTIVE);
		Event savedEvent = eventRepository.save(event);
		return EventConverter.toDTO(savedEvent);
	}

	public List<EventDTO> getAllEvents(String name) {
		if (name == null)
			return eventRepository.findAll().stream().map(EventConverter::toDTO).collect(Collectors.toList());
		else
			return eventRepository.findByName(name).stream().map(EventConverter::toDTO).collect(Collectors.toList());
	}

	public EventDTO getEventById(Long id) {
		Event event = eventRepository.findById(id).orElse(null);
		return Objects.nonNull(event) ? EventConverter.toDTO(event) : null;
	}

	public boolean isEventActive(Long id) {
		Event event = eventRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Event Not Found"));
		return EventStatus.ACTIVE.equals(event.getStatus());
	}

	public boolean isEventValid(Long id) {
		Event event = eventRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Event Not Found"));
		return Objects.nonNull(event)&& (EventStatus.ACTIVE.equals(event.getStatus())||EventStatus.SOLD_OUT.equals(event.getStatus()));
	}
	
	public void deleteEvent(Long id) {
		eventRepository.deleteById(id);
	}

	public EventDTO updateEvent(Long id, EventDTO dto) {
		Event event = eventRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Event Not Found"));
		event.setStatus(EventStatus.valueOf(dto.getStatus()));
		event = EventConverter.toEntity(dto, event);
		return EventConverter.toDTO(eventRepository.save(event));
	}

	public List<EventDetailsDTO> getTicketAvailabilityDetails(Long eventId) {
		boolean valid = isEventValid(eventId);
		if (!valid) {
			throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Event is Marked as DONE");
		}
		return bookingClient.getTicketAvailabilityDetails(eventId);

	}

}
