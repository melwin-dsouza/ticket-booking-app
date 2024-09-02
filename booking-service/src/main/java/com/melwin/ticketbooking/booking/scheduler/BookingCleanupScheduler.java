package com.melwin.ticketbooking.booking.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.melwin.ticketbooking.booking.entity.Purchase;
import com.melwin.ticketbooking.booking.entity.PurchaseStatus;
import com.melwin.ticketbooking.booking.repository.PurchaseRepository;
import com.melwin.ticketbooking.booking.repository.TicketRepository;

@Service
public class BookingCleanupScheduler {

	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private TicketRepository ticketRepository;

	@Scheduled(fixedRate = 60000) // Run every 60 seconds
	@Transactional
	public void cleanupIncompleteBookings() {
		System.out.println("Incomplete purchase cleanup scheduler started");
		LocalDateTime now = LocalDateTime.now();
		List<Purchase> incompletePurchases = purchaseRepository.findIncompletePurchaseOrders(now.minusMinutes(5)); // Assuming bookings older than 5 minutes are stale
		for (Purchase purchase : incompletePurchases) {
			// Mark the Purchase as failed
			purchase.setStatus(PurchaseStatus.FAILED);
			purchaseRepository.save(purchase);
			// Release reserved tickets
			ticketRepository.updateCancelledTickets(purchase.getId());
		}
		System.out.println("Incomplete purchase cleanup scheduler Ended");
	}


}
