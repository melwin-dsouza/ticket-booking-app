package com.melwin.ticketbooking.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
	
	private Long id;
	private String userId;
	private String eventId;
	private String message;

}
