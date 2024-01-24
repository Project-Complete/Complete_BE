package org.complete.domain.member.oauth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.complete.domain.member.oauth.domain.PrincipalDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

public class AuthTokenProvider {

    private final Key key;

    public AuthTokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createAuthToken(String id, String username, Date expiry) {
        return new AuthToken(id, username, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken) {

        if(authToken.validate()) {

            Claims claims = authToken.getTokenClaims();

            UserDetails userDetails = PrincipalDetails.from(claims.getSubject());

            return new UsernamePasswordAuthenticationToken(userDetails, authToken);
        } else {
            throw new RuntimeException("토큰 검증 실패");
        }
    }
}
