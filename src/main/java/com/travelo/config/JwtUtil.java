package com.travelo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${jwt.expiration.time}")
    private long expirationTime;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        try {
            // Ensure consistent and secure key generation
            byte[] keyBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
            
            // Base64 encode to ensure consistent key length
            byte[] encodedKey = Base64.getEncoder().encode(keyBytes);
            
            // Ensure exactly 256 bits (32 bytes)
            byte[] finalKeyBytes = new byte[32];
            System.arraycopy(encodedKey, 0, finalKeyBytes, 0, Math.min(encodedKey.length, 32));
            
            secretKey = Keys.hmacShaKeyFor(finalKeyBytes);
            
            // Log key details for debugging
            logger.info("JWT Secret Key Generated - Length: {} bytes", finalKeyBytes.length);
            logger.debug("Base64 Encoded Key Preview: {}", 
                Base64.getEncoder().encodeToString(finalKeyBytes).substring(0, 10) + "...");
        } catch (Exception e) {
            logger.error("Error initializing JWT secret key", e);
            throw new RuntimeException("Failed to initialize JWT secret key", e);
        }
    }

    public String generateToken(String username, Set<String> roles) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("authorities", roles);
            String token = createToken(claims, username);
            
            // Log token generation for debugging
            logger.debug("Generated Token for user: {}", username);
            logger.trace("Full Token: {}", token);
            
            return token;
        } catch (Exception e) {
            logger.error("Token generation failed", e);
            throw new RuntimeException("Failed to generate JWT token", e);
        }
    }

    private String createToken(Map<String, Object> claims, String subject) {
    	
    	
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            boolean isValid = extractedUsername.equals(username) && !isTokenExpired(token);
            
            logger.debug("Token Validation Result - Username: {}, Valid: {}", username, isValid);
            
            return isValid;
        } catch (Exception e) {
            logger.error("Token validation failed", e);
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
        	
        	System.out.println("secretKey"+secretKey);
        	
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            return claimsResolver.apply(claims);
        } catch (ExpiredJwtException e) {
            // Handle expired token separately if needed
            throw new ExpiredJwtException(null, null, "JWT token has expired", e.getCause());
        }
    }

    // Diagnostic method to help troubleshoot token issues
    public void diagnosticTokenCheck(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            logger.info("Diagnostic Check: Token is valid");
        } catch (Exception e) {
            logger.error("Diagnostic Check Failed", e);
            logger.error("Token Details:");
            logger.error("Token Length: {}", token.length());
            logger.error("Token Preview: {}", token.substring(0, Math.min(token.length(), 50)) + "...");
        }
    }
}