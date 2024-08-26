package com.melwin.ticketbooking.event.dto;

import java.time.LocalDateTime;

import com.melwin.ticketbooking.event.entity.EventStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
	
	private Long id;
	private String name;
	private LocalDateTime date;
	private String location;
	private String performer;
	private String status;
}
