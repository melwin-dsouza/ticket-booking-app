package com.melwin.ticketbooking.booking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.melwin.ticketbooking.booking.entity.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	
	@Query(value= "SELECT * FROM t_purchase where created_at<?1 and status='IN_PROGRESS'", nativeQuery = true)
	List<Purchase> findIncompletePurchaseOrders(LocalDateTime timestamp);

}
