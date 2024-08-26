package com.melwin.ticketbooking.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melwin.ticketbooking.booking.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
