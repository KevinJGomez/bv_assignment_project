package com.bv.assignment.leave_request_api.util;

import com.bv.assignment.leave_request_api.models.RequestUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(RequestUser requestUserDetails) {
        logger.info("JwtTokenUtil | Generating token for user: {}", requestUserDetails.getUsername());
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", requestUserDetails.getRole().name());
        return createToken(claims, requestUserDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        logger.info("JwtTokenUtil | Creating token for subject: {}", subject);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20)) // 20 minutes
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token, RequestUser userDetails) {
        logger.info("JwtTokenUtil | Validating token for user: {}", userDetails.getUsername());
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            logger.error("JwtTokenUtil | Token validation failed", e);
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            logger.error("JwtTokenUtil | Token expiration check failed", e);
            return true;
        }
    }

    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            logger.error("JwtTokenUtil | Extracting username from token failed", e);
            return null;
        }
    }

    public String extractRole(String token) {
        try {
            return extractClaim(token, claims -> claims.get("role", String.class));
        } catch (Exception e) {
            logger.error("JwtTokenUtil | Extracting role from token failed", e);
            return null;
        }
    }

    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            logger.error("JwtTokenUtil | Extracting expiration date from token failed", e);
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            logger.error("JwtTokenUtil | Extracting claim from token failed", e);
            return null;
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            logger.error("JwtTokenUtil | Token has expired", e);
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("JwtTokenUtil | Token is unsupported", e);
            throw e;
        } catch (MalformedJwtException e) {
            logger.error("JwtTokenUtil | Token is malformed", e);
            throw e;
        } catch (SecurityException e) {
            logger.error("JwtTokenUtil | Token signature validation failed", e);
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error("JwtTokenUtil | Token string is empty or null", e);
            throw e;
        }
    }
}
