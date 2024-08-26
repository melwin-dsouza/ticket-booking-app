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
import com.melwin.ticketbooking.booking.dto.PurchaseDTO;
import com.melwin.ticketbooking.booking.dto.PurchaseRequest;
import com.melwin.ticketbooking.booking.dto.UserDTO;
import com.melwin.ticketbooking.booking.entity.TicketType;
import com.melwin.ticketbooking.booking.service.PurchaseService;
import com.melwin.ticketbooking.booking.service.UserService;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

	@MockBean
	private UserService userService;
	@Autowired
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void getAllUsers() throws Exception {
		UserDTO user = new UserDTO();
		user.setId(1L);
		UserDTO user2 = new UserDTO();
		user2.setId(2L);
		List<UserDTO> list = new ArrayList<>(Arrays.asList(user, user2));
		when(userService.getAllUsers()).thenReturn(list);

		mockMvc.perform(MockMvcRequestBuilders.get("/user/all").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200)).andExpect(MockMvcResultMatchers.jsonPath("$.data.[*].id").isNotEmpty());
	}

	@Test
	void getUserById() throws Exception {
		UserDTO user = new UserDTO();
		user.setId(1L);
		when(userService.getUserById(anyLong())).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.get(String.format("/user/%s", 1)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200)).andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1L));
	}

	@Test
	void createUser() throws Exception {
		UserDTO user = new UserDTO();
		user.setId(1L);
		when(userService.createUser(any(UserDTO.class))).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new UserDTO(1L, "testName", "578458", "test@gmail.com", null)))
				.accept(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8))
				.andExpect(status().is(201));
		Mockito.verify(this.userService, Mockito.times(1)).createUser(Mockito.any(UserDTO.class));
	}

	@Test
	void deleteUser() throws Exception {
		Mockito.doNothing().when(userService).deleteUser(anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/user/%s", 1)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(204));
	}

	@Test
	void updateUser() throws Exception {
		UserDTO user = new UserDTO();
		user.setId(1L);
		when(userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.put(String.format("/user/%s", 1)).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new UserDTO(1L, "testName", "5758", "test@gmail.com", null)))
				.accept(MediaType.APPLICATION_JSON).characterEncoding(StandardCharsets.UTF_8))
				.andExpect(status().is(200));
		Mockito.verify(this.userService, Mockito.times(1)).updateUser(anyLong(), Mockito.any(UserDTO.class));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
