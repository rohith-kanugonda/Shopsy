package com.shopsy.shopsy.Service.Implementation;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

    @Value("${SECRET_KEY}")
    private String secretKey;

    public String generateToken(String email) {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);

            String token = Jwts.builder()
                    .setSubject(email)
                    .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256)
                    .compact();

            return token;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String verifyToken(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                .build()
                .parseClaimsJws(token);

        return claims.getBody().getSubject();
    }

    // Verify token Method
    public boolean isTokenValid(String token) {
        try {
            // Verify the token
            String email = verifyToken(token);
            // Token is valid if email is not null
            return email != null;
        } catch (Exception e) {
            return false; // Token verification failed
        }
    }
}
