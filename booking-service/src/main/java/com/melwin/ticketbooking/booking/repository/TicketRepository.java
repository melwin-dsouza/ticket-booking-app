package com.melwin.ticketbooking.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.melwin.ticketbooking.booking.dto.EventDetailsDTO;
import com.melwin.ticketbooking.booking.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	@Query(value = " SELECT count(*) FROM t_ticket WHERE event_id=?1 AND ticket_type=?2 AND status='AVAILABLE'", nativeQuery = true)
	Long checkTicketAvailability(Long eventId, String type);

	@Modifying
	@Query(value = " UPDATE t_ticket SET status=?1, purchase_id=?2,user_id=?3 WHERE event_id=?4 AND ticket_type=?5 AND status='AVAILABLE' FETCH FIRST ?6 ROWS ONLY", nativeQuery = true)
	Integer updateTicketStatus(String status, Long purchase,Long user, Long eventId, String type, Integer quantity);

	@Query(value = "SELECT price FROM t_ticket WHERE event_id=?1 AND ticket_type=?2 FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
	Double getTicketAmount(Long eventId, String type);

	@Modifying
	@Query(value = " UPDATE t_ticket SET status=?1 WHERE purchase_id=?2 ", nativeQuery = true)
	Integer updateTicketStatus(String status, Long purchase);
	
	@Query(value= "SELECT ticket_type as ticketType,COUNT(*) as availableTickets FROM t_ticket where event_id=?1 AND status='AVAILABLE' GROUP BY ticket_type", nativeQuery = true)
	List<EventDetailsDTO> getTicketAvailabiltyDetails(Long eventId);
	
	@Modifying
	@Query(value = "UPDATE t_ticket set status='AVAILABLE', purchase_id=null, user_id=null WHERE purchase_id=?1 ", nativeQuery = true)
	Integer updateCancelledTickets(Long purchase);

}
