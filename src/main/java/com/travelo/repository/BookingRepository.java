package com.travelo.repository;

import org.springframework.stereotype.Repository;

import com.travelo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	
	
}