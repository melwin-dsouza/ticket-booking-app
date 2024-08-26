package com.melwin.ticketbooking.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="t_ticket")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PROTECTED)
	@EqualsAndHashCode.Include
	@Column(name ="id")
	private Long id;

	@Column(name ="event_id")
	private Long eventId;
	
	@Enumerated(EnumType.STRING)
	@Column(name ="ticket_type")
	private TicketType ticketType;
	
	@Column(name ="price")
	private Double price;
	
	@Enumerated(EnumType.STRING)
	@Column(name ="status")
	private TicketStatus status;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(targetEntity = Purchase.class)
	@JoinColumn(name = "purchase_id")
	private Purchase purchase;
}
