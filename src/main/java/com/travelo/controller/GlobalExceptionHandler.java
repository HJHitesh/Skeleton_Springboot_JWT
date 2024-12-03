package com.travelo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.travelo.model.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle Expired JWT Exception
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
    	
    	System.out.println("Caught by controller");
        // Build a custom error response
        ErrorResponse errorResponse = new ErrorResponse(
                "TOKEN_EXPIRED", 
                "The provided JWT token has expired. Please login again to get a new token."
        );
        
        // Return a ResponseEntity with a 401 status code (Unauthorized)
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // You can add other exception handlers as needed.
}
