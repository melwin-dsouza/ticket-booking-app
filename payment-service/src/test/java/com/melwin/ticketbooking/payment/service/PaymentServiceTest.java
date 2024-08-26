package com.melwin.ticketbooking.payment.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.melwin.ticketbooking.payment.dto.PaymentRequest;
import com.melwin.ticketbooking.payment.dto.PaymentResponse;
import com.melwin.ticketbooking.payment.entity.Payment;
import com.melwin.ticketbooking.payment.repository.PaymentRepository;



@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private PaymentResponsePublisher responsePublisher;
	@InjectMocks
	private PaymentService paymentService;

	Payment payment;
	PaymentRequest dto;

	@BeforeEach
	public void setup() {
		payment = new Payment();
		payment.setAmount(500.0);
		payment.setPurchaseId(1L);
		dto = new PaymentRequest();
		dto.setPurchaseId(1L);
		dto.setStatus("SUCCESS");
	}

	@Test
	void completePayment() {
		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
		Mockito.doNothing().when(responsePublisher).sendMessage(any(PaymentResponse.class));
		paymentService.completePayment(dto);
		verify(paymentRepository, times(2)).save(any(Payment.class));
		verify(responsePublisher, times(1)).sendMessage(any(PaymentResponse.class));
	}

	

}