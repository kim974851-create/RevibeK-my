package com.ssafy.revibek.auth;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.revibek.user.dto.UserAuthDto;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Getter
    @Value("${jwt.access-token-expiration-ms}")
    private long accessTokenExpirationMs;

    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (secretBytes.length < 32) {
            throw new IllegalStateException("jwt.secret must be at least 32 bytes.");
        }
        secretKey = Keys.hmacShaKeyFor(secretBytes);
    }

    public String createAccessToken(UserAuthDto user) {
        return createToken(user, accessTokenExpirationMs);
    }

    public String createRefreshToken(UserAuthDto user) {
        return createToken(user, refreshTokenExpirationMs);
    }

    public String getUserId(String token) {
        return parseToken(token).getSubject();
    }

    public String getEmail(String token) {
        Object email = parseToken(token).get("email");
        return email == null ? null : email.toString();
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private String createToken(UserAuthDto user, long expiresInMs) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(expiresInMs);

        return Jwts.builder()
            .subject(user.getId())
            .claim("email", user.getEmail())
            .claim("provider", user.getProvider())
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiration))
            .signWith(secretKey)
            .compact();
    }

    private io.jsonwebtoken.Claims parseToken(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
