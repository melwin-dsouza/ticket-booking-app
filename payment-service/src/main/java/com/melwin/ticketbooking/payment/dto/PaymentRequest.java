package com.melwin.ticketbooking.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
	
	private Long purchaseId;
	private Long userId;
	private Double amount;
	private String type;
	
}