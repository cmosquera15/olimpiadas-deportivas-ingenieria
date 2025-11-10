package com.ingenieria.olimpiadas.olimpiadas_deportivas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class JwtTokenProvider {

    private final SecretKey key;
    private final long expirationMs;
    private final String issuer;

    /**
     * @param secret
     * @param expirationMs 
     * @param issuer
     */
    public JwtTokenProvider(String secret, long expirationMs, String issuer) {
        this.key = buildKey(secret);
        this.expirationMs = expirationMs;
        this.issuer = (issuer == null || issuer.isBlank()) ? "olimpiadas-ingenieria" : issuer;
    }

    private SecretKey buildKey(String secret) {
        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(secret);
        } catch (IllegalArgumentException e) {
            bytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        if (bytes.length < 32) {
            byte[] padded = new byte[32];
            for (int i = 0; i < 32; i++) padded[i] = bytes[i % bytes.length];
            bytes = padded;
        }
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(String subjectEmail, Collection<String> authorities) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        String authClaim = (authorities == null) ? "" :
                authorities.stream().filter(Objects::nonNull).collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(subjectEmail)
                .issuer(issuer)
                .issuedAt(now)
                .expiration(exp)
                .claim("auth", authClaim)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getEmail(String token) {
        return getAllClaims(token).getSubject();
    }

    public String getEmailFromToken(String token) {
        return getEmail(token);
    }

    public List<String> getAuthorities(String token) {
        Claims claims = getAllClaims(token);
        Object raw = claims.get("auth");
        if (raw == null) return List.of();
        String s = String.valueOf(raw).trim();
        if (s.isEmpty()) return List.of();
        return Arrays.stream(s.split(",")).map(String::trim).filter(a -> !a.isEmpty()).collect(Collectors.toList());
    }

    public Date getExpiration(String token) {
        return getAllClaims(token).getExpiration();
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}
