package com.travelo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelo.model.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private AuthService authService;  // Use AuthService instead of UserDetailsService


    @Autowired
    @Lazy 
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Check if the authorization header is present and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Extract JWT token from the header
          // Extract username from the token
            
            try {
            	  username = jwtUtil.extractUsername(jwt);  // Extract username from the token
            } catch (ExpiredJwtException e) {
           
                // Log the expired token error
                logger.warn("JWT Token has expired: " + e.getMessage());

                // Set HTTP status to Unauthorized (401)
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                // Create a custom response message
                ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()),"JWT Token has expired. Please log in again.");

                // Convert the error response object to JSON
                try {
                    String jsonResponse = new ObjectMapper().writeValueAsString(errorResponse);
                    response.getWriter().write(jsonResponse); // Write the JSON response
                    return;
                } catch (IOException ioException) {
                    logger.error("Error writing JSON response", ioException);
                }

            }
        }

        // Validate the token and set authentication in the context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.authService.loadUserByUsername(username);

            // Validate the JWT token
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Set the authentication in the context
            }
        }

        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
