package com.melwin.ticketbooking.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
	
	private Long purchaseId;
	private Long userId;
	private Double amount;
	private String type;
	
}