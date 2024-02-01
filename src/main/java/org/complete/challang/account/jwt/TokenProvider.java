package org.complete.challang.account.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.complete.challang.account.user.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.complete.challang.account.jwt.util.TokenUtil.*;

@Component
public class TokenProvider {

    @Value("${jwt.access-token.expiration}")
    private Long accessTokenExpirationDate;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpirationDate;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public Token createAccessToken(User user) {
        Claims claims = Jwts.claims();
        claims.put(ID_CLAIM, user.getId());
        claims.put(ROLE_CLAIM, user.getRoleType());
        return new Token(ACCESS_TOKEN_SUBJECT, claims, accessTokenExpirationDate, secretKey);
    }

    public Token createRefreshToken(User user) {
        Claims claims = Jwts.claims();
        claims.put(ID_CLAIM, user.getId());
        claims.put(EMAIL_CLAIM, user.getEmail());
        claims.put(ROLE_CLAIM, user.getRoleType());
        return new Token(REFRESH_TOKEN_SUBJECT, claims, refreshTokenExpirationDate, secretKey);
    }

    public Token convertToken(String token) {
        return new Token(token, secretKey);
    }
}
