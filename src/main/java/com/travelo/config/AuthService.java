package com.travelo.config;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.travelo.dto.APIResponse;
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

	public ResponseEntity<APIResponse> login(AuthenticationRequest authenticationRequest) throws Exception {

		APIResponse apiResponse = new APIResponse();
		try {
		// Authenticate the username and password
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		// Load the user and generate the JWT token
		final UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());

		
			apiResponse.setData(Arrays.asList(jwtUtil.generateToken((User) userDetails)));
			apiResponse.setMessage(HttpStatus.ACCEPTED.toString());

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
		} catch (Exception e) {

			apiResponse.setData(Arrays.asList(String.valueOf(HttpStatus.UNAUTHORIZED.value())));
			apiResponse.setMessage(HttpStatus.UNAUTHORIZED.toString());

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
