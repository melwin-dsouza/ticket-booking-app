package com.melwin.ticketbooking.booking.converter;

import com.melwin.ticketbooking.booking.dto.PurchaseDTO;
import com.melwin.ticketbooking.booking.entity.Purchase;

public class PurchaseConverter {
	
	public static PurchaseDTO toDTO(Purchase purchase) {
		PurchaseDTO dto=new PurchaseDTO();
		dto.setPurchaseId(purchase.getId());
		dto.setEventId(purchase.getEventId());
		dto.setUserId(purchase.getUser().getId());
		dto.setAmount(purchase.getAmount());
		dto.setStatus(purchase.getStatus().toString());
		dto.setTickets(purchase.getTickets().stream().map(TicketConverter::toDTO).toList());
		return dto;
	}

}
