package com.melwin.ticketbooking.booking.dto;

import com.melwin.ticketbooking.booking.entity.TicketStatus;
import com.melwin.ticketbooking.booking.entity.TicketType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

	private Long ticketId;
	private Long eventId;
	private TicketType ticketType;
	private Double price;
	private TicketStatus status;

}
