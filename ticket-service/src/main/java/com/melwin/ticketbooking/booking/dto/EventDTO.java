package com.melwin.ticketbooking.booking.dto;

import java.time.LocalDateTime;

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
