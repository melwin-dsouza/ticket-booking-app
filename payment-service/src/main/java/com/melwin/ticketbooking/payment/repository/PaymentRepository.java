package com.melwin.ticketbooking.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melwin.ticketbooking.payment.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByPurchaseId(Long id);

}
