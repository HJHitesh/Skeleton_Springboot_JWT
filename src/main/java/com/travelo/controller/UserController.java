package com.travelo.controller;

import com.travelo.model.User;
import com.travelo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
    	
    	boolean isPresent = userService.findByUsername(user.getUsername()).isPresent();
        // Check if user already exists
        if (!isPresent) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Conflict status if user exists
        }

        User createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
