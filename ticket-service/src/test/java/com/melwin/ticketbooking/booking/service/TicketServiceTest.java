package com.melwin.ticketbooking.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.melwin.ticketbooking.booking.client.EventClient;
import com.melwin.ticketbooking.booking.dto.EventDetailsDTO;
import com.melwin.ticketbooking.booking.dto.TicketBulkDTO;
import com.melwin.ticketbooking.booking.dto.TicketDTO;
import com.melwin.ticketbooking.booking.entity.Ticket;
import com.melwin.ticketbooking.booking.entity.TicketType;
import com.melwin.ticketbooking.booking.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

	@Mock
	private TicketRepository ticketRepository;
	@Mock
	private EventClient eventClient;
	@InjectMocks
	private TicketService ticketService;

	Ticket ticket;
	TicketDTO dto;

	@BeforeEach
	public void setup() {
		ticket = new Ticket();
		ticket.setEventId(1L);
		ticket.setPrice(200.0);
		dto = new TicketDTO(1L, 11L, TicketType.NORMAL, 200.0, null);
	}

	@Test
	void createTicket() {
		when(eventClient.isEventValid(anyLong())).thenReturn(true);
		when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
		TicketDTO response = ticketService.createTicket(dto);
		assertEquals(response.getEventId(), 1L);
		verify(eventClient, times(1)).isEventValid(anyLong());
		verify(ticketRepository, times(1)).save(any(Ticket.class));
	}

	@Test
	void getAllTickets() {
		List<Ticket> list = new ArrayList<>(Arrays.asList(ticket));
		when(ticketRepository.findAll()).thenReturn(list);
		List<TicketDTO> allTickets = ticketService.getAllTickets();
		assertEquals(allTickets.size(), 1);
	}

	@Test
	void getTicketById() {
		when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
		TicketDTO response = ticketService.getTicketById(1L);
		assertEquals(response.getEventId(), 1);
		verify(ticketRepository, times(1)).findById(anyLong());
	}

	@Test
	void deleteTicket() {
		doNothing().when(ticketRepository).deleteById(anyLong());
		ticketService.deleteTicket(1L);
		verify(ticketRepository, times(1)).deleteById(anyLong());
	}

	@Test
	void updateTicket() {
		when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
		when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
		TicketDTO updatedTicket = ticketService.updateTicket(1L, dto);
		assertEquals(updatedTicket.getEventId(), 11);
		verify(ticketRepository, times(1)).save(any(Ticket.class));
	}

	@Test
	void createMultipleTickets() {
		when(eventClient.isEventValid(anyLong())).thenReturn(true);
		when(ticketRepository.saveAll(any())).thenReturn(List.of(ticket));
		Integer updatedCount = ticketService.createMultipleTickets(new TicketBulkDTO(1L, null, 100.0, null, 10));
		assertEquals(updatedCount, 1);
	}

	@Test
	void getTicketAvailabilityDetails() {
		when(eventClient.isEventValid(anyLong())).thenReturn(true);
		when(ticketRepository.getTicketAvailabiltyDetails(1L)).thenReturn(List.of());
		List<EventDetailsDTO> list = ticketService.getTicketAvailabilityDetails(1L);
		assertEquals(list.size(), 0);
	}

}