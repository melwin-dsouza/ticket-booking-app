package com.melwin.ticketbooking.event.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.melwin.ticketbooking.event.dto.EventDTO;
import com.melwin.ticketbooking.event.service.EventService;

@WebMvcTest(controllers = EventController.class)
public class EventControllerTest {

	@MockBean
	private EventService eventService;
	@Autowired
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void getAllEvents() throws Exception {
		EventDTO event = new EventDTO();
		event.setId(1L);
		EventDTO event2 = new EventDTO();
		event2.setId(2L);
		List<EventDTO> list = new ArrayList<>(Arrays.asList(event, event2));
		when(eventService.getAllEvents(anyString())).thenReturn(list);

		mockMvc.perform(MockMvcRequestBuilders.get("/events").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));
	}

	@Test
	void getEventById() throws Exception {
		EventDTO event = new EventDTO();
		event.setId(1L);
		when(eventService.getEventById(anyLong())).thenReturn(event);

		mockMvc.perform(MockMvcRequestBuilders.get(String.format("/events/%s", 1)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200)).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
	}

	@Test
	void createEvent() throws Exception {
		EventDTO event = new EventDTO();
		event.setId(1L);
		when(eventService.createEvent(any(EventDTO.class))).thenReturn(event);

		mockMvc.perform(MockMvcRequestBuilders.post("/events").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new EventDTO(1L, "test", null, "loc", "performer", null)))
				.accept(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8))
				.andExpect(status().is(201));
		Mockito.verify(this.eventService, Mockito.times(1)).createEvent(Mockito.any(EventDTO.class));
	}

	@Test
	void isEventActive() throws Exception {
		when(eventService.isEventActive(anyLong())).thenReturn(true);

		mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("/events/%s/isActive", 1)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));
		Mockito.verify(this.eventService, Mockito.times(1)).isEventActive(anyLong());
	}

	@Test
	void deleteEvent() throws Exception {
		Mockito.doNothing().when(eventService).deleteEvent(anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/events/%s", 1)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(204));
	}

	@Test
	void updateEvent() throws Exception {
		EventDTO event = new EventDTO();
		event.setId(1L);
		when(eventService.updateEvent(anyLong(), any(EventDTO.class))).thenReturn(event);

		mockMvc.perform(
				MockMvcRequestBuilders.put(String.format("/events/%s", 1)).contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(new EventDTO(1L, "test", null, "loc", "performer", null)))
						.accept(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8))
				.andExpect(status().is(200));
		Mockito.verify(this.eventService, Mockito.times(1)).updateEvent(anyLong(), Mockito.any(EventDTO.class));
	}

	@Test
	void isEventValid() throws Exception {
		when(eventService.isEventValid(anyLong())).thenReturn(true);

		mockMvc.perform(
				MockMvcRequestBuilders.get(String.format("/events/%s/isValid", 1)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));
		Mockito.verify(this.eventService, Mockito.times(1)).isEventValid(anyLong());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
