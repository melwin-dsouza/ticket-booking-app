package com.melwin.ticketbooking.booking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.melwin.ticketbooking.booking.converter.UserConverter;
import com.melwin.ticketbooking.booking.dto.UserDTO;
import com.melwin.ticketbooking.booking.entity.User;
import com.melwin.ticketbooking.booking.exception.ApiRequestException;
import com.melwin.ticketbooking.booking.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserDTO createUser(UserDTO userDto) {
		User user = UserConverter.toEntity(userDto, new User());
		User savedUser = userRepository.save(user);
		return UserConverter.toDTO(savedUser);
	}

	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(UserConverter::toDTO).collect(Collectors.toList());
	}

	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User Not Found"));
		return UserConverter.toFullDTO(user);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public UserDTO updateUser(Long id, UserDTO dto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "User Not Found"));
		user = UserConverter.toEntity(dto, user);
		return UserConverter.toFullDTO(userRepository.save(user));
	}

}
