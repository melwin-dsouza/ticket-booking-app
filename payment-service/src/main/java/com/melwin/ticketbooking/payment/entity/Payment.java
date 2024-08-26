package com.melwin.ticketbooking.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="t_payment")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PROTECTED)
	@EqualsAndHashCode.Include
	@Column(name ="id")
	private Long id;

	@Column(name ="purchase_id")
	private Long purchaseId;
	
	@Column(name ="user_id")
	private Long userId;
	
	@Column(name ="amount")
	private Double amount;
	
	@Enumerated(EnumType.STRING)
	@Column(name ="status")
	private PaymentStatus status;

}
