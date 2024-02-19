package org.complete.challang.account.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.complete.challang.account.jwt.Token;
import org.complete.challang.account.jwt.TokenProvider;
import org.complete.challang.account.jwt.util.TokenUtil;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.account.user.domain.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Token refreshToken = tokenProvider.convertToken(TokenUtil.extractRefreshToken(request)
                    .orElse(null));
            refreshToken.validateToken();
            if (refreshToken.getToken() != null) {
                checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            } else {
                checkAccessToken(request);
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString("Access Token is expired"));
            response.getWriter().flush();
        }
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response,
                                                        Token refreshToken) {
        userRepository.findByRefreshToken(refreshToken.getToken())
                .ifPresent(user -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    String newAccess = tokenProvider.createAccessToken(user)
                            .getToken();
                    String newRefresh = reIssueRefreshToken(user);
                    TokenUtil.setAccessTokenHeader(response, newAccess);
                    TokenUtil.setRefreshTokenHeader(response, newRefresh);
                });
    }

    private String reIssueRefreshToken(User user) {
        String reIssueRefreshToken = tokenProvider.createRefreshToken(user)
                .getToken();
        user.updateRefreshToken(reIssueRefreshToken);
        userRepository.saveAndFlush(user);
        return reIssueRefreshToken;
    }

    private void checkAccessToken(HttpServletRequest request) {
        Token accessToken = tokenProvider.convertToken(TokenUtil.extractAccessToken(request)
                .orElse(null));

        if (accessToken.validateToken()) {
            Map<String, Object> tokenPayload = accessToken.getPayload();
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(Integer.toString((Integer) tokenPayload.get(TokenUtil.ID_CLAIM)))
                    .password("")
                    .roles((String) tokenPayload.get(TokenUtil.ROLE_CLAIM))
                    .build();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
