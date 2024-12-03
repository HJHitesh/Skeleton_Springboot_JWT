package com.travelo.config;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.travelo.dto.ApiResponse;
import com.travelo.dto.AuthenticationRequest;
import com.travelo.model.User;
import com.travelo.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	public ResponseEntity<ApiResponse> login(AuthenticationRequest authenticationRequest) throws Exception {

		ApiResponse apiResponse = new ApiResponse();
		try {
		
			System.out.println("Received password: " + authenticationRequest.getPassword());
			
		// Authenticate the username and password
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		// Load the user and generate the JWT token
		final UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());

			apiResponse.setStatus("success");
			apiResponse.setMessage("Login successful");
			apiResponse.setError(null);
			
			String username = userDetails.getUsername();
		    Set<String> roles = userDetails.getAuthorities().stream()
		                                  .map(GrantedAuthority::getAuthority)
		                                  .collect(Collectors.toSet());

			
			String token = jwtUtil.generateToken(username, roles); 
			
			apiResponse.setData(Map.of("token", token));
	
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
		} catch (Exception e) {
			
			apiResponse.setStatus("error");
			apiResponse.setMessage("Unauthorized access. Please check your credentials");
			apiResponse.setError(new ApiResponse.ErrorDetails("401", "Unauthorized"));
			apiResponse.setData(null);
	
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);

		}

	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		// Convert the authorities to GrantedAuthority objects
		Set<SimpleGrantedAuthority> grantedAuthorities = user.getAuthorities().stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);
	}
}
