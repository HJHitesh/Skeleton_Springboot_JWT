package com.travelo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelo.config.AuthService;
import com.travelo.dto.APIResponse;
import com.travelo.dto.AuthenticationRequest;


@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    	System.out.println("Data in login");
    	return authService.login(authenticationRequest);
    }
    
}
