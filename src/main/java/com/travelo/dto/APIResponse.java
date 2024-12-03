package com.travelo.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiResponse {

    private String status;
    private String message;
    private ErrorDetails error;
    private Map<String, Object> data;
    
    public ApiResponse() {
    	
    }

    // Constructor
    public ApiResponse(String status, String message, ErrorDetails error, Object data) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.data = new HashMap<>();
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDetails getError() {
        return error;
    }

    public void setError(ErrorDetails error) {
        this.error = error;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    // Inner class for error details
    public static class ErrorDetails {

        private String code;
        private String description;

        // Constructor
        public ErrorDetails(String code, String description) {
            this.code = code;
            this.description = description;
        }

        // Getters and Setters
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
