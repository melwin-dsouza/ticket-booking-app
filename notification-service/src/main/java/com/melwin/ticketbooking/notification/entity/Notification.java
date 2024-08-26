package com.melwin.ticketbooking.notification.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="t_notification")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PROTECTED)
	@EqualsAndHashCode.Include
	@Column(name ="id")
	private Long id;

	@Column(name ="user_id")
	private String userId;
	@Column(name ="user_email")
	private String userEmail;
	@Column(name ="user_name")
	private String userName;
	
	@Column(name ="event_id")
	private String eventId;
	@Column(name ="event_name")
	private String eventName;
	@Column(name ="event_location")
	private String eventLocation;
	@Column(name ="event_date")
	private LocalDateTime eventDate;
	
	@Column(name ="message")
	private String message;

	@Column(name ="ticket_type")
	private String ticketType;
	@Column(name ="ticket_qty")
	private Integer ticketQty;
	@Column(name ="payment_amount")
	private Double paymentAmount;

}
