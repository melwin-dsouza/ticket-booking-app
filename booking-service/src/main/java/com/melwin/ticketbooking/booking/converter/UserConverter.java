package com.melwin.ticketbooking.booking.converter;

import com.melwin.ticketbooking.booking.dto.UserDTO;
import com.melwin.ticketbooking.booking.entity.User;

public class UserConverter {
	
	public static User toEntity(UserDTO dto,User user) {
		user.setName(dto.getName());
		user.setContactNumber(dto.getContactNumber());
		user.setEmail(dto.getEmail());
		return user;
	}
	
	public static UserDTO toDTO(User user) {
		UserDTO dto=new UserDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setContactNumber(user.getContactNumber());
		dto.setEmail(user.getEmail());
		return dto;
	}
	
	public static UserDTO toFullDTO(User user) {
		UserDTO dto=new UserDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setContactNumber(user.getContactNumber());
		dto.setEmail(user.getEmail());
		dto.setTickets(user.getTickets().stream().map(TicketConverter::toDTO).toList());
		return dto;
	}

}
