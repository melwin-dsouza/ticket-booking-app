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

import com.melwin.ticketbooking.booking.dto.UserDTO;
import com.melwin.ticketbooking.booking.entity.User;
import com.melwin.ticketbooking.booking.repository.UserRepository;



@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;
	
	User user;
	UserDTO dto;

	@BeforeEach
	public void setup() {
		user = new User();
		user.setName("testName");
		dto = new UserDTO(1L, "testName", "97124845", "test@gmail.com", null);
	}

	@Test
	void createUser() {
		when(userRepository.save(any(User.class))).thenReturn(user);
		UserDTO response = userService.createUser(dto);
		assertEquals(response.getName(), "testName");
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void getAllUsers() {
		List<User> list = new ArrayList<>(Arrays.asList(user));
		when(userRepository.findAll()).thenReturn(list);
		List<UserDTO> allUsers = userService.getAllUsers();
		assertEquals(allUsers.size(), 1);
	}

	@Test
	void getUserById() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		UserDTO response = userService.getUserById(1L);
		assertEquals(response.getName(), "testName");
		verify(userRepository, times(1)).findById(anyLong());
	}

	@Test
	void deleteUser() {
		doNothing().when(userRepository).deleteById(anyLong());
		userService.deleteUser(1L);
		verify(userRepository, times(1)).deleteById(anyLong());
	}

	@Test
	void updateUser() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);
		UserDTO updatedUser = userService.updateUser(1L, dto);
		assertEquals(updatedUser.getName(), "testName");
		verify(userRepository, times(1)).save(any(User.class));
	}
	
}