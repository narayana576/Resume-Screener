package com.resumescreener.backend.security; 
 
import io.jsonwebtoken.*; 
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.stereotype.Component; 
 
import javax.crypto.spec.SecretKeySpec; 
import java.nio.charset.StandardCharsets; 
import java.security.Key; 
import java.util.Date; 
 
@Component 
public class JwtUtil { 
 
    @Value("${app.jwtSecret}") 
    private String jwtSecret; 
 
    private final int jwtExpirationMs = 86400000; // 24 hours 
 
    private Key getSigningKey() { 
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8); 
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName()); 
    } 
 
    public String generateJwtToken(String email) { 
        return Jwts.builder() 
                .setSubject(email) 
                .setIssuedAt(new Date()) 
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs)) 
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) 
                .compact(); 
    } 
 
    public String getEmailFromJwtToken(String token) { 
        return Jwts.parserBuilder() 
                .setSigningKey(getSigningKey()) 
                .build() 
                .parseClaimsJws(token) 
                .getBody() 
                .getSubject(); 
    } 
 
    public boolean validateJwtToken(String authToken) { 
        try { 
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(authToken);
            return true; 
        } catch (JwtException | IllegalArgumentException e) { 
            // invalid token 
        } 
        return false; 
    } 
}