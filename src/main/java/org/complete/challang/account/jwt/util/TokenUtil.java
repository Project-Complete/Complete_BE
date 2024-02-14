package org.complete.challang.account.jwt.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class TokenUtil {

    public static final String ACCESS_TOKEN_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Authorization-refresh";
    public static final String BEARER = "Bearer ";
    public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    public static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    public static final String ID_CLAIM = "id";
    public static final String EMAIL_CLAIM = "email";
    public static final String ROLE_CLAIM = "role";

    public static void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(ACCESS_TOKEN_HEADER, accessToken);
    }

    public static void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(REFRESH_TOKEN_HEADER, refreshToken);
    }

    public static Optional<String> extractAccessToken(HttpServletRequest request) {
        return extractToken(request, ACCESS_TOKEN_HEADER);
    }

    public static Optional<String> extractRefreshToken(HttpServletRequest request) {
        return extractToken(request, REFRESH_TOKEN_HEADER);
    }

    private static Optional<String> extractToken(HttpServletRequest request, String tokenHeader) {
        return Optional.ofNullable(request.getHeader(tokenHeader))
                .filter(token -> token.startsWith(BEARER))
                .map(token -> token.replace(BEARER, ""));
    }
}
