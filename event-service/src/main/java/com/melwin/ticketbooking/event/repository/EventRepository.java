package com.melwin.ticketbooking.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melwin.ticketbooking.event.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	
	List<Event> findByName(String name);

}
