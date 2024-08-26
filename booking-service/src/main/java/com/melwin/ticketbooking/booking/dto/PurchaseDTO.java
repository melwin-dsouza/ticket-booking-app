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
public class PurchaseDTO {

	private Long purchaseId;
	private Long eventId;
	private Long userId;
	private String status;
	private Double amount;
	List<TicketDTO> tickets;
	
}
