package org.complete.challang.app.account.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.complete.challang.app.account.oauth2.userinfo.OAuth2UserInfo;
import org.complete.challang.app.account.oauth2.userinfo.OAuth2UserInfoFactory;
import org.complete.challang.app.account.user.domain.entity.SocialType;
import org.complete.challang.app.account.user.domain.entity.User;
import org.complete.challang.app.account.user.domain.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return this.process(userRequest, oAuth2User);
    }

    private OAuth2User process(OAuth2UserRequest userRequest,
                               OAuth2User oAuth2User) {
        SocialType socialType = SocialType.valueOf(userRequest.getClientRegistration()
                .getRegistrationId()
                .toUpperCase());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(socialType, oAuth2User.getAttributes());
        User user = getUser(oAuth2UserInfo, socialType);

        return user.toOAuth2User(oAuth2User.getAttributes());
    }

    private User getUser(OAuth2UserInfo oAuth2UserInfo, SocialType socialType) {
        User user = userRepository.findBySocialTypeAndSocialId(socialType, oAuth2UserInfo.getSocialId())
                .orElse(null);

        if (user == null) {
            User createdUser = oAuth2UserInfo.toEntity(socialType);
            return userRepository.saveAndFlush(createdUser);
        }

        return user;
    }
}
