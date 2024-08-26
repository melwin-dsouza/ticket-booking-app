package com.melwin.ticketbooking.booking.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_user")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PROTECTED)
	@EqualsAndHashCode.Include
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "contact_number")
	private String contactNumber;
	
	@Column(name = "email")
	private String email;

	@OneToMany(targetEntity = Ticket.class,mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Ticket> tickets = new ArrayList<>();

}
