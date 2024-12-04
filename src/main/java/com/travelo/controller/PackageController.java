package com.travelo.controller;


import com.travelo.dto.BookingRequest;
import com.travelo.model.Booking;
import com.travelo.model.Packages;
import com.travelo.model.User;
import com.travelo.repository.BookingRepository;
import com.travelo.repository.PackagesRepository;
import com.travelo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackagesRepository packageRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository; // Assuming User entity exists

    // Search packages by destination and budget
    @GetMapping("/search")
    public List<Packages> searchPackages(@RequestParam String destination, @RequestParam String budget) {
        Double budgetDouble = Double.parseDouble(budget); // Convert budget to double
        return packageRepository.searchPackages(destination, budgetDouble);
    }

    // Book a package
    @PostMapping("/book")
    public Booking bookPackage(@RequestBody BookingRequest bookingRequest) {
        // Retrieve the user and package details
        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Packages travelPackage = packageRepository.findById(bookingRequest.getPackageId())
                .orElseThrow(() -> new RuntimeException("Package not found"));

        // Create a new Booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTravelPackage(travelPackage);
        booking.setBookingDate(new Date());
        booking.setTotalPrice(bookingRequest.getTotalPrice());
        booking.setPaymentStatus("PENDING");

        // Save the booking
        return bookingRepository.save(booking);
    }

    // Get package details by ID
    @GetMapping("/{id}")
    public Packages getPackageDetails(@PathVariable Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
    }
}

