package com.ktd.ytts.config;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Date;

@Configuration
public class JwtUtils {

    @Value("${custom.jwt-validity-hour}")
    private Long validityHour;

    private final SecretKey key = Jwts.SIG.HS256.key().build();

    @Bean
    public String generateJwtToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + validityHour * 3600)) // 1 day validity
                .signWith(key)
                .compact();
    }

    @Bean
    public String getSubjectFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
