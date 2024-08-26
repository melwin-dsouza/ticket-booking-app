package com.melwin.ticketbooking.booking.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDetails {
	private Long userId;
	private String userEmail;
	private String userName;
	private Long eventId;
	private String eventName;
	private LocalDateTime eventDate;
	private String eventLocation;
	private String ticketType;
	private Integer ticketQty;
	private Double paymentAmount;
	private String bookingStatus;

}
