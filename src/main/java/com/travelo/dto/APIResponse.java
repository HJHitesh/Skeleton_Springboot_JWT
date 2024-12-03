package com.travelo.dto;

import java.util.List;

public class APIResponse {  // Use generics to allow flexibility in data type
	
	private int statusCode;
	private List data;  // Change to List to hold multiple items
	private String message;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}