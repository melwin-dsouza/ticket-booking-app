package com.melwin.ticketbooking.booking.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;
	private String name;
	private String contactNumber;
	private String email;
	private List<TicketDTO> tickets;

}
