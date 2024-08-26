package com.melwin.ticketbooking.booking.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_purchase")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PROTECTED)
	@EqualsAndHashCode.Include
	@Column(name = "id")
	private Long id;

	@Column(name = "event_id")
	private Long eventId;
	
	@Column(name = "amount")
	private Double amount;
	
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private PurchaseStatus status;

	@OneToMany(targetEntity = Ticket.class, mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Ticket> tickets;

}
