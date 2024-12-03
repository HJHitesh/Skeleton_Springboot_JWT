package com.travelo.controller;

import com.travelo.dto.APICreateUser;
import com.travelo.model.Role;
import com.travelo.model.User;
import com.travelo.repository.RoleRepository;
import com.travelo.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    RoleRepository roleService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody APICreateUser apiCreateUser) {
    	
    	User user= new User();
    	boolean isPresent = userService.findByUsername(apiCreateUser.getUsername()).isPresent();
        // Check if user already exists
        if (isPresent) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Conflict status if user exists
        }
        
        user.setUsername(apiCreateUser.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(apiCreateUser.getPassword());
        user.setPassword(hashedPassword);
        
        Optional<Role> role = roleService.findById(1L);
        Set<Role> roles = new HashSet<>();
        roles.add(role.get());
        
        //setting user role
        user.setRoles(roles);
        
        User createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
