package com.melwin.ticketbooking.event.entity;

import java.time.LocalDateTime;

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
@Table(name="t_event")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PROTECTED)
	@EqualsAndHashCode.Include
	@Column(name ="id")
	private Long id;

	@Column(name ="name")
	private String name;
	
	@Column(name ="date")
	private LocalDateTime date;
	
	@Column(name ="location")
	private String location;
	
	@Column(name ="performer")
	private String performer;

	@Enumerated(EnumType.STRING)
	@Column(name ="status")
	private EventStatus status;

}
