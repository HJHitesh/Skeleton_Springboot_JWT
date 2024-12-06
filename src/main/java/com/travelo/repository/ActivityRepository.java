package com.travelo.repository;

import org.springframework.stereotype.Repository;

import com.travelo.model.Activity;
import com.travelo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
	
	
	
}