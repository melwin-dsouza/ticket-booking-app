package com.melwin.ticketbooking.booking.converter;

import com.melwin.ticketbooking.booking.dto.TicketBulkDTO;
import com.melwin.ticketbooking.booking.dto.TicketDTO;
import com.melwin.ticketbooking.booking.entity.Ticket;

public class TicketConverter {
	
	public static Ticket toEntity(TicketDTO dto,Ticket ticket) {
		ticket.setEventId(dto.getEventId());
		ticket.setTicketType(dto.getTicketType());
		ticket.setPrice(dto.getPrice());
		ticket.setStatus(dto.getStatus());
		return ticket;
	}
	
	public static Ticket toEntity(TicketBulkDTO dto) {
		Ticket ticket=new Ticket();
		ticket.setEventId(dto.getEventId());
		ticket.setTicketType(dto.getTicketType());
		ticket.setPrice(dto.getPrice());
		ticket.setStatus(dto.getStatus());
		return ticket;
	}
	
	public static TicketDTO toDTO(Ticket ticket) {
		TicketDTO dto=new TicketDTO();
		dto.setTicketId(ticket.getId());
		dto.setEventId(ticket.getEventId());
		dto.setPrice(ticket.getPrice());
		dto.setTicketType(ticket.getTicketType());
		dto.setStatus(ticket.getStatus());
		return dto;
	}

}
