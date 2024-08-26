package com.melwin.ticketbooking.notification.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDetails {
	private String userId;
	private String userEmail;
	private String userName;
	private String eventId;
	private String eventName;
	private LocalDateTime eventDate;
	private String eventLocation;
	private String ticketType;
	private Integer ticketQty;
	private Double paymentAmount;
	private String bookingStatus;

}
