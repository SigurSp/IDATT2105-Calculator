package com.example.calculator.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
  private static final String SECRET_KEY = "your-secret-key-at-least-32-chars-long"; // Must be 32+ chars for HS256
  private static final long EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes

  public static String generateToken(String username) {
    return Jwts.builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8))) // Updated signing method
        .compact();
  }

  public static String extractUsername(String token) {
    return Jwts.parser()
        .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  public static boolean isTokenValid(String token) {
    try {
      Jwts.parser()
          .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}