package org.complete.challang.app.account.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class Token {

    @Getter
    private final String token;

    private final Key key;

    public Token(String subject,
                 Claims claims,
                 Long tokenExpirationDate,
                 String secretKey) {
        this.key = getKey(secretKey);
        token = generateKey(subject, claims, tokenExpirationDate, key);
    }

    public Token(String token, String secretKey) {
        this.token = token;
        this.key = getKey(secretKey);
    }

    public String generateKey(String subject,
                              Claims claims,
                              Long tokenExpirationDate,
                              Key key) {
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(createExpireDate(tokenExpirationDate))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken() {
        if (token == null) {
            return false;
        }

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return true;
    }

    public Map<String, Object> getPayload() {
        final Claims body = extractBody();
        return body.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()));
    }

    private Date createExpireDate(Long tokenExpirationDate) {
        return new Date(System.currentTimeMillis() + tokenExpirationDate);
    }

    private Claims extractBody() {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
