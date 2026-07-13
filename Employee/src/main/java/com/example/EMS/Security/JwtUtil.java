package com.example.EMS.Security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Generate Employee Token
     */
    public String generateEmployeeToken(Integer employeeId, String email) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("id", employeeId);
        claims.put("role", "EMPLOYEE");

        return createToken(claims, email);
    }

    /**
     * Generate Admin Token
     */
    public String generateAdminToken(Integer adminId, String username) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("id", adminId);
        claims.put("role", "ADMIN");

        return createToken(claims, username);
    }

    /**
     * Create JWT
     */
    private String createToken(Map<String, Object> claims, String subject) {

        Date now = new Date();

        Date expiry = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Secret Key
     */
    private Key getSignKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Extract All Claims
     */
    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extract Subject
     */
    public String extractUsername(String token) {

        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    /**
     * Extract User Id
     */
    public Integer extractId(String token) {

        return extractAllClaims(token)
                .get("id", Integer.class);
    }

    /**
     * Extract Role
     */
    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }

    /**
     * Generic Claim Extractor
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        Claims claims = extractAllClaims(token);

        return resolver.apply(claims);
    }

    /**
     * Check Expired
     */
    public boolean isTokenExpired(String token) {

        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    /**
     * Validate Token
     */
    public boolean validateToken(String token) {

        return !isTokenExpired(token);
    }

}