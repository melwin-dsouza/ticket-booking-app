package com.melwin.ticketbooking.booking.dto;

import com.melwin.ticketbooking.booking.entity.TicketType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {

	private Long eventId;
	private Long userId;
	private Integer quantity;
	private TicketType type;
	
}