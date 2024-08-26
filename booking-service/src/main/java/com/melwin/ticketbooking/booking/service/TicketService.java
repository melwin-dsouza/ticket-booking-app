package com.melwin.ticketbooking.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.melwin.ticketbooking.booking.client.EventClient;
import com.melwin.ticketbooking.booking.converter.TicketConverter;
import com.melwin.ticketbooking.booking.dto.EventDetailsDTO;
import com.melwin.ticketbooking.booking.dto.TicketBulkDTO;
import com.melwin.ticketbooking.booking.dto.TicketDTO;
import com.melwin.ticketbooking.booking.entity.Ticket;
import com.melwin.ticketbooking.booking.entity.TicketStatus;
import com.melwin.ticketbooking.booking.exception.ApiRequestException;
import com.melwin.ticketbooking.booking.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private EventClient eventClient;

	public TicketDTO createTicket(TicketDTO ticketDto) {
		boolean valid=eventClient.isEventValid(ticketDto.getEventId());
		if (!valid) {
			throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Event is Marked as DONE");
		}
		Ticket ticket = TicketConverter.toEntity(ticketDto, new Ticket());
		ticket.setStatus(TicketStatus.AVAILABLE);
		Ticket savedTicket = ticketRepository.save(ticket);
		return TicketConverter.toDTO(savedTicket);
	}

	public List<TicketDTO> getAllTickets() {
		return ticketRepository.findAll().stream().map(TicketConverter::toDTO).collect(Collectors.toList());
	}

	public TicketDTO getTicketById(Long id) {
		Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Ticket Not Found"));
		return TicketConverter.toDTO(ticket);
	}

	public void deleteTicket(Long id) {
		ticketRepository.deleteById(id);
	}

	public TicketDTO updateTicket(Long id, TicketDTO dto) {
		Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Ticket Not Found"));
		ticket = TicketConverter.toEntity(dto, ticket);
		return TicketConverter.toDTO(ticketRepository.save(ticket));
	}

	public Integer createMultipleTickets(TicketBulkDTO ticketDTO) {
		List<Ticket> tickets=new ArrayList<>();
		boolean valid=eventClient.isEventValid(ticketDTO.getEventId());
		if (!valid) {
			throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Event is Marked as DONE");
		}
		for(int i=0;i<ticketDTO.getQuantity();i++) {
			Ticket ticket = TicketConverter.toEntity(ticketDTO);
			ticket.setStatus(TicketStatus.AVAILABLE);
			tickets.add(ticket);
		}
		return ticketRepository.saveAll(tickets).size();
	}

	public List<EventDetailsDTO> getTicketAvailabilityDetails(Long eventId) {
		boolean valid=eventClient.isEventValid(eventId);
		if (!valid) {
			throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Event is Marked as DONE");
		}
		return ticketRepository.getTicketAvailabiltyDetails(eventId);

	}

}
