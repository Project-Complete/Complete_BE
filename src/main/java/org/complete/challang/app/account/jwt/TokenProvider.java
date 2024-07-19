package org.complete.challang.app.account.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.complete.challang.app.account.oauth2.CustomOAuth2User;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.account.jwt.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        claims.put(TokenUtil.ID_CLAIM, user.getId());
        claims.put(TokenUtil.ROLE_CLAIM, user.getRoleType());
        return new Token(TokenUtil.ACCESS_TOKEN_SUBJECT, claims, accessTokenExpirationDate, secretKey);
    }

    public Token createAccessToken(CustomOAuth2User customOauth2User) {
        Claims claims = Jwts.claims();
        claims.put(TokenUtil.ID_CLAIM, customOauth2User.getUserId());
        claims.put(TokenUtil.ROLE_CLAIM, customOauth2User.getRoleType());
        return new Token(TokenUtil.ACCESS_TOKEN_SUBJECT, claims, accessTokenExpirationDate, secretKey);
    }

    public Token createRefreshToken(User user) {
        Claims claims = Jwts.claims();
        claims.put(TokenUtil.ID_CLAIM, user.getId());
        claims.put(TokenUtil.EMAIL_CLAIM, user.getEmail());
        claims.put(TokenUtil.ROLE_CLAIM, user.getRoleType());
        return new Token(TokenUtil.REFRESH_TOKEN_SUBJECT, claims, refreshTokenExpirationDate, secretKey);
    }

    public Token createRefreshToken(CustomOAuth2User customOAuth2User) {
        Claims claims = Jwts.claims();
        claims.put(TokenUtil.ID_CLAIM, customOAuth2User.getUserId());
        claims.put(TokenUtil.EMAIL_CLAIM, customOAuth2User.getEmail());
        claims.put(TokenUtil.ROLE_CLAIM, customOAuth2User.getRoleType());
        return new Token(TokenUtil.REFRESH_TOKEN_SUBJECT, claims, refreshTokenExpirationDate, secretKey);
    }

    public Token convertToken(String token) {
        return new Token(token, secretKey);
    }
}
