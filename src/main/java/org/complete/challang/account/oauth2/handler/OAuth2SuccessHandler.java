package org.complete.challang.account.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.complete.challang.account.jwt.Token;
import org.complete.challang.account.jwt.TokenProvider;
import org.complete.challang.account.jwt.util.TokenUtil;
import org.complete.challang.account.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import org.complete.challang.account.oauth2.userinfo.OAuth2UserInfo;
import org.complete.challang.account.oauth2.userinfo.OAuth2UserInfoFactory;
import org.complete.challang.account.oauth2.util.CookieUtils;
import org.complete.challang.account.user.domain.entity.SocialType;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.account.user.domain.repository.UserRepository;
import org.complete.challang.common.exception.ErrorCode;
import org.complete.challang.common.exception.FilterErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static org.complete.challang.account.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${front.callback-uri}")
    private String callBackUri;

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final FilterErrorResponse filterErrorResponse;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        try {
            User user = findUserByAuthentication(authentication);
            /*if (customOAuth2User.getRoleType().equals(RoleType.GUEST)) {
                Token token = tokenProvider.createAccessToken(user);
                TokenUtil.setAccessTokenHeader(response, token.getToken());
            } else {
                loginSuccess(response, user);
            }*/

            String redirectUri = loginSuccess(request, response, user);

            response.setStatus(HttpServletResponse.SC_OK);
            clearAuthenticationAttributes(request, response);
            getRedirectStrategy().sendRedirect(request, response, redirectUri);
        } catch (Exception e) {
            filterErrorResponse.toJson(response, e, ErrorCode.OAUTH2_LOGIN_FAILURE);
        }
    }

    private String loginSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                User user) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);

        if (!redirectUri.isPresent()) {
            throw new IllegalArgumentException("redirect uri가 존재하지 않음");
        }

        Token accessToken = tokenProvider.createAccessToken(user);
        Token refreshToken = tokenProvider.createRefreshToken(user);
        TokenUtil.setAccessTokenHeader(response, accessToken.getToken());
        TokenUtil.setRefreshTokenHeader(response, refreshToken.getToken());
        user.updateRefreshToken(refreshToken.getToken());
        userRepository.saveAndFlush(user);

        return makeRedirectUri(redirectUri.get(), accessToken.getToken(), refreshToken.getToken());
    }

    private String makeRedirectUri(String targetUri,
                                   String accessToken,
                                   String refreshToken) {
        return UriComponentsBuilder.fromUriString(callBackUri)
                .queryParam("access_token", accessToken)
                .queryParam("refresh_token", refreshToken)
                .queryParam("redirect_uri", targetUri)
                .build()
                .toUriString();
    }

    private User findUserByAuthentication(Authentication authentication) {
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        SocialType socialType = SocialType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(socialType, principal.getAttributes());
        return findUserBySocialTypeAndSocialId(socialType, oAuth2UserInfo.getSocialId());
    }

    private User findUserBySocialTypeAndSocialId(SocialType socialType,
                                                 String socialId) {
        // 예외처리 필요
        return userRepository.findBySocialTypeAndSocialId(socialType, socialId)
                .orElseThrow();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request,
                                               HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
