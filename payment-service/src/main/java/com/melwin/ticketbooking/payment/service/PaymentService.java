package com.melwin.ticketbooking.payment.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.melwin.ticketbooking.payment.dto.PaymentRequest;
import com.melwin.ticketbooking.payment.dto.PaymentResponse;
import com.melwin.ticketbooking.payment.entity.Payment;
import com.melwin.ticketbooking.payment.entity.PaymentStatus;
import com.melwin.ticketbooking.payment.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	PaymentResponsePublisher responsePublisher;

	private static final Random RANDOM = new Random();

	public void completePayment(PaymentRequest request) {
		Payment payment;
		if("BOOK".equals(request.getType())){
			payment = new Payment();
			payment.setAmount(request.getAmount());
			payment.setPurchaseId(request.getPurchaseId());
			payment.setUserId(request.getUserId());
			payment.setStatus(PaymentStatus.IN_PROGRESS);
			payment = paymentRepository.save(payment);
			try {
				Thread.sleep(2 * 60 * 1000);//initiates payment with Payment provider
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			payment.setStatus(randomStatusGenerator());
			payment = paymentRepository.save(payment);
			responsePublisher.sendMessage(new PaymentResponse(request.getPurchaseId(), payment.getStatus()));
		}else if("CANCEL".equals(request.getType())){
			Optional<Payment> paymentOpt = paymentRepository.findByPurchaseId(request.getPurchaseId());
			if(paymentOpt.isPresent()) {
				payment = paymentOpt.get();
				payment.setStatus(PaymentStatus.CANCELLED);
				payment = paymentRepository.save(payment);
			}
			responsePublisher.sendMessage(new PaymentResponse(request.getPurchaseId(), PaymentStatus.CANCELLED));
		}		
		
	}

	private PaymentStatus randomStatusGenerator() {
		List<PaymentStatus> list = Arrays.asList(PaymentStatus.SUCCESS, PaymentStatus.FAILED);
		return list.get(RANDOM.nextInt(list.size()));
	}

}
