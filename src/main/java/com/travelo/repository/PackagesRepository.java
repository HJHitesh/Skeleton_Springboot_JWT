package com.travelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travelo.model.Packages;

@Repository
public interface PackagesRepository extends JpaRepository<Packages , Long> {
	
	@Query("SELECT p FROM Packages p WHERE p.country = :destination AND p.packagePrice <= :budget")
	List<Packages> searchPackages(@Param("destination") String destination, @Param("budget") Double budget);

	

}
