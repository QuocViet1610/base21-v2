package xtel.base21v2.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import xtel.base21v2.infrastructure.exception.CustomException;
import xtel.base21v2.infrastructure.shared.model.TokenInfo;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtService {

    private String secret;
    private Long expiration;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalStateException("JWT secret can not be null or empty");
        }
        if (expiration == null || expiration <= 0) {
            throw new IllegalStateException("JWT expiration must be greater than 0");
        }
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long id, String username) {

        if (id == null || username == null || username.trim().isEmpty()) {
            throw new CustomException("ID and username must be provided");
        }

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(username)
                .claim("id", id)
                .claim("jit", UUID.randomUUID().toString())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        try {
             return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            log.error("[TOKEN_INVALID]: {}", e.getMessage());
            throw new CustomException("TOKEN_INVALID");
        }
    }

    public TokenInfo getTokenInfo(String token) {
        Jws<Claims> claimsJws = validateToken(token);
        Claims claims = claimsJws.getPayload();
        return TokenInfo.builder()
                .id(claims.get("id", Long.class))
                .username(claims.getSubject())
                .jit(claims.get("jit", String.class))
                .issuedAt(claims.getIssuedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .expiration(claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }
}
