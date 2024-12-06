package com.travelo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelo.model.Activity;
import com.travelo.model.Packages;
import com.travelo.repository.PackagesRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/packages")
public class AdminController {

	@Autowired
	private PackagesRepository packagesRepository;

	
	@RequestMapping("/hello")
	public String helloFromTravelo() {
		return "Hello Travelo";
	}

	@PostMapping
	public Packages createPackage(@RequestBody Packages packages) {

		// @concep -1 : Bidirectional Relationship: Before saving the package
		// we loop through the activities and set their packageDetails to the current
		// Packages instance.
		// This maintains the integrity of the relationship.

		// @concept 2 - Owning Side and Inverse Side: JPA
		Optional.ofNullable(packages.getActivities())
				.ifPresent(activities -> activities.forEach(activity -> activity.setPackageDetails(packages)));

		//@concept 3 : Optionoal of means it cannot contain the null values gives null pointer exception.
		Optional.of(packages)
				.map(packagesRepository :: save)
				.map(savedPackage -> ResponseEntity.status(HttpStatus.CREATED).body(savedPackage))
				.orElse(ResponseEntity.badRequest().build());
				
		return packagesRepository.save(packages);
	}

	@GetMapping
	public ResponseEntity<List<Packages>> getAllPackages() {
		List<Packages> packagesList = packagesRepository.findAll();
		
		return Optional.of(packagesList)
				.filter(list -> !list.isEmpty())
				.map(ResponseEntity :: ok)
				.orElseGet(()-> ResponseEntity.noContent().build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Packages> getPackageById(@PathVariable Long id) {
		Optional<Packages> packageDetails = packagesRepository.findById(id);
		return packageDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/{id}")
	public ResponseEntity<Packages> updatePackage(@PathVariable Long id, @RequestBody Packages packageDetails) {
	    Optional<Packages> optionalPackage = packagesRepository.findById(id);
	    if (optionalPackage.isPresent()) {
	        Packages existingPackage = optionalPackage.get();

	        // Update the package details
	        existingPackage.setPackageName(packageDetails.getPackageName());
	        existingPackage.setPackageDuration(packageDetails.getPackageDuration());
	        existingPackage.setStayDetails(packageDetails.getStayDetails());
	        existingPackage.setCountry(packageDetails.getCountry());
	        existingPackage.setFlightDetails(packageDetails.getFlightDetails());
	        existingPackage.setPackagePrice(packageDetails.getPackagePrice());
	        existingPackage.setPackageImage(packageDetails.getPackageImage());

	        // Override all existing activities with new ones from the request
	        List<Activity> newActivities = packageDetails.getActivities();
	        
	        // Ensure the activities list is associated with the package
	        for (Activity activity : newActivities) {
	            activity.setPackageDetails(packageDetails);//Ensure each activity is linked to this package
	        }

	        // Set the new activities to the package
	        existingPackage.setActivities(newActivities);

	        // Save the updated package with the new activities
	        packagesRepository.save(existingPackage);
	        
	        return ResponseEntity.ok(existingPackage);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
		if (packagesRepository.existsById(id)) {
			packagesRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
