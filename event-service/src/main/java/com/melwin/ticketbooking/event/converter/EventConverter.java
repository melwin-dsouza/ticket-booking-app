package com.melwin.ticketbooking.event.converter;

import java.util.Objects;

import com.melwin.ticketbooking.event.dto.EventDTO;
import com.melwin.ticketbooking.event.entity.Event;

public class EventConverter {
	
	public static Event toEntity(EventDTO dto,Event event) {
		event.setName(dto.getName());
		event.setDate(dto.getDate());
		event.setLocation(dto.getLocation());
		event.setPerformer(dto.getPerformer());
		return event;
	}
	
	public static EventDTO toDTO(Event event) {
		EventDTO dto=new EventDTO();
		dto.setId(event.getId());
		dto.setName(event.getName());
		dto.setDate(event.getDate());
		dto.setLocation(event.getLocation());
		dto.setPerformer(event.getPerformer());
		dto.setStatus(Objects.nonNull(event.getStatus()) ? event.getStatus().toString() : null);
		return dto;
	}

}
