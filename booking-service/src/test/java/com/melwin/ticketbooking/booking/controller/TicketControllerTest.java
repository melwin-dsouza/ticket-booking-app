package com.melwin.ticketbooking.booking.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melwin.ticketbooking.booking.dto.EventDetailsDTO;
import com.melwin.ticketbooking.booking.dto.PurchaseDTO;
import com.melwin.ticketbooking.booking.dto.PurchaseRequest;
import com.melwin.ticketbooking.booking.dto.TicketBulkDTO;
import com.melwin.ticketbooking.booking.dto.TicketDTO;
import com.melwin.ticketbooking.booking.dto.TicketDTO;
import com.melwin.ticketbooking.booking.entity.TicketType;
import com.melwin.ticketbooking.booking.service.PurchaseService;
import com.melwin.ticketbooking.booking.service.TicketService;
import com.melwin.ticketbooking.booking.service.UserService;

@WebMvcTest(controllers = TicketController.class)
public class TicketControllerTest {

	@MockBean
	private TicketService ticketService;
	@Autowired
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void getAllTickets() throws Exception {
		TicketDTO ticket = new TicketDTO();
		ticket.setTicketId(1L);;
		TicketDTO ticket2 = new TicketDTO();
		ticket2.setTicketId(2L);
		List<TicketDTO> list = new ArrayList<>(Arrays.asList(ticket, ticket2));
		when(ticketService.getAllTickets()).thenReturn(list);

		mockMvc.perform(MockMvcRequestBuilders.get("/ticket/all").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200)).andExpect(MockMvcResultMatchers.jsonPath("$.data.[*].ticketId").isNotEmpty());
	}

	@Test
	void getTicketById() throws Exception {
		TicketDTO ticket = new TicketDTO();
		ticket.setTicketId(1L);
		when(ticketService.getTicketById(anyLong())).thenReturn(ticket);

		mockMvc.perform(MockMvcRequestBuilders.get(String.format("/ticket/%s", 1)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200)).andExpect(MockMvcResultMatchers.jsonPath("$.data.ticketId").value(1L));
	}

	@Test
	void createTicket() throws Exception {
		TicketDTO ticket = new TicketDTO();
		ticket.setEventId(1L);
		when(ticketService.createTicket(any(TicketDTO.class))).thenReturn(ticket);

		mockMvc.perform(MockMvcRequestBuilders.post("/ticket").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new TicketDTO(1L, 11L,TicketType.NORMAL,200.0, null)))
				.accept(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8))
				.andExpect(status().is(201));
		Mockito.verify(this.ticketService, Mockito.times(1)).createTicket(Mockito.any(TicketDTO.class));
	}
	
	@Test
	void createMultipleTickets() throws Exception {
		TicketBulkDTO ticket = new TicketBulkDTO();
		ticket.setEventId(1L);
		when(ticketService.createMultipleTickets(any(TicketBulkDTO.class))).thenReturn(2);

		mockMvc.perform(MockMvcRequestBuilders.post("/ticket/multiple").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new TicketBulkDTO(1L, TicketType.NORMAL,200.0, null, 2)))
				.accept(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8))
				.andExpect(status().is(201))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(2L));
		Mockito.verify(this.ticketService, Mockito.times(1)).createMultipleTickets(Mockito.any(TicketBulkDTO.class));
	}

	@Test
	void deleteTicket() throws Exception {
		Mockito.doNothing().when(ticketService).deleteTicket(anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/ticket/%s", 1)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(204));
	}

	@Test
	void updateTicket() throws Exception {
		TicketDTO ticket = new TicketDTO();
		ticket.setTicketId(1L);
		when(ticketService.updateTicket(anyLong(), any(TicketDTO.class))).thenReturn(ticket);

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("/ticket/%s", 1)).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new TicketDTO(1L, 11L,TicketType.NORMAL,200.0, null)))
				.accept(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8))
				.andExpect(status().is(200));
		Mockito.verify(this.ticketService, Mockito.times(1)).updateTicket(anyLong(), Mockito.any(TicketDTO.class));
	}

	@Test
	void getTicketAvailabilityDetails() throws Exception {
		List<EventDetailsDTO> list = new ArrayList<>();
		when(ticketService.getTicketAvailabilityDetails(anyLong())).thenReturn(list);

		mockMvc.perform(MockMvcRequestBuilders.get(String.format("/ticket/%s/details", 1)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200)).andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
