package org.complete.challang.account.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.complete.challang.account.jwt.Token;
import org.complete.challang.account.jwt.TokenProvider;
import org.complete.challang.account.jwt.util.TokenUtil;
import org.complete.challang.account.oauth2.CustomOAuth2User;
import org.complete.challang.account.oauth2.userinfo.OAuth2UserInfo;
import org.complete.challang.account.oauth2.userinfo.OAuth2UserInfoFactory;
import org.complete.challang.account.user.domain.entity.SocialType;
import org.complete.challang.account.user.domain.entity.User;
import org.complete.challang.account.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${front.redirect-uri}")
    private String frontRedirectUri;

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        try {
            CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            User user = findUserByAuthentication(authentication);
            /*if (customOAuth2User.getRoleType().equals(RoleType.GUEST)) {
                Token token = tokenProvider.createAccessToken(user);
                TokenUtil.setAccessTokenHeader(response, token.getToken());
            } else {
                loginSuccess(response, user);
            }*/

            String redirectUrl = loginSuccess(response, user);
            response.setStatus(HttpServletResponse.SC_OK);
            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } catch (Exception e) {
            throw e;
        }
    }

    private String makeRedirectUrl(String accessToken, String refreshToken) {
        return UriComponentsBuilder.fromUriString(frontRedirectUri)
                .queryParam("access_token", accessToken)
                .queryParam("refresh_token", refreshToken)
                .build()
                .toUriString();
    }

    private String loginSuccess(HttpServletResponse response, User user) {
        Token accessToken = tokenProvider.createAccessToken(user);
        Token refreshToken = tokenProvider.createRefreshToken(user);
        TokenUtil.setAccessTokenHeader(response, accessToken.getToken());
        TokenUtil.setRefreshTokenHeader(response, refreshToken.getToken());
        user.updateRefreshToken(refreshToken.getToken());
        userRepository.saveAndFlush(user);

        return makeRedirectUrl(accessToken.getToken(), refreshToken.getToken());
    }

    private User findUserByAuthentication(Authentication authentication) {
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        SocialType socialType = SocialType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(socialType, principal.getAttributes());
        return findUserBySocialTypeAndSocialId(socialType, oAuth2UserInfo.getSocialId());
    }

    private User findUserBySocialTypeAndSocialId(SocialType socialType, String socialId) {
        // 예외처리 필요
        return userRepository.findBySocialTypeAndSocialId(socialType, socialId)
                .orElseThrow();
    }
}
