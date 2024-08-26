package com.melwin.ticketbooking.payment.dto;

import com.melwin.ticketbooking.payment.entity.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
	
	private Long purchaseId;
	private PaymentStatus status;
	
}