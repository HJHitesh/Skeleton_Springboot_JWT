package com.travelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelo.model.Packages;

@Repository
public interface PackagesRepository extends JpaRepository<Packages , Long> {
	

}
