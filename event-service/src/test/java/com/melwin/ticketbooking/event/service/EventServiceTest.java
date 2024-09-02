package com.melwin.ticketbooking.event.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.melwin.ticketbooking.event.dto.EventDetailsDTO;
import com.melwin.ticketbooking.event.client.BookingClient;
import com.melwin.ticketbooking.event.dto.EventDTO;
import com.melwin.ticketbooking.event.entity.Event;
import com.melwin.ticketbooking.event.entity.EventStatus;
import com.melwin.ticketbooking.event.exception.ApiRequestException;
import com.melwin.ticketbooking.event.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

	@Mock
	private EventRepository eventRepository;
	
	@Mock
	private BookingClient bookingClient;

	@InjectMocks
	private EventService eventService;

	Event event;
	EventDTO dto;

	@BeforeEach
	public void setup() {
		event = new Event();
		event.setName("test");
		event.setStatus(EventStatus.ACTIVE);
		dto = new EventDTO(1L, "test", null, "loc", "org", "ACTIVE");
	}

	@Test
	void createEvent() {
		when(eventRepository.save(any(Event.class))).thenReturn(event);
		EventDTO response = eventService.createEvent(dto);
		assertEquals(response.getName(), "test");
		verify(eventRepository, times(1)).save(any(Event.class));
	}

	@Test
	void getAllEvents() {
		List<Event> list = new ArrayList<>(Arrays.asList(event));
		when(eventRepository.findAll()).thenReturn(list);
		List<EventDTO> allEvents = eventService.getAllEvents(null);
		assertEquals(allEvents.size(), 1);
	}

	@Test
	void getEventById() {
		when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
		EventDTO response = eventService.getEventById(1L);
		assertEquals(response.getName(), "test");
		verify(eventRepository, times(1)).findById(anyLong());
	}

	@Test
	void deleteEvent() {
		doNothing().when(eventRepository).deleteById(anyLong());
		eventService.deleteEvent(1L);
		verify(eventRepository, times(1)).deleteById(anyLong());
	}

	@Test
	void updateEvent() {
		when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
		when(eventRepository.save(any(Event.class))).thenReturn(event);
		EventDTO updatedEvent = eventService.updateEvent(1L, dto);
		assertEquals(updatedEvent.getName(), "test");
		verify(eventRepository, times(1)).save(any(Event.class));
	}

	@Test
	void isEventActive() {
		when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));

		boolean response = eventService.isEventActive(1L);
		assertTrue(response);
	}

	@Test
	void isEventValid() {
		when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
		boolean response = eventService.isEventValid(1L);
		assertTrue(response);
	}

	@Test
	void getTicketAvailabilityDetails() {
		when(eventRepository.findById(anyLong())).thenReturn(Optional.of(event));
		when(bookingClient.getTicketAvailabilityDetails(anyLong())).thenReturn(List.of());
		List<EventDetailsDTO> list = eventService.getTicketAvailabilityDetails(1L);
		assertEquals(list.size(), 0);
	}
	

	@Test
	void getTicketAvailabilityDetailsException() {
		when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());
		assertThrows(ApiRequestException.class,()->{ eventService.getTicketAvailabilityDetails(1L);});
	}
}