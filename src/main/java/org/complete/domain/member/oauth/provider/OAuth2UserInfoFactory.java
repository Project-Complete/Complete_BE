package org.complete.domain.member.oauth.provider;

import org.complete.domain.member.oauth.domain.ProviderType;
import org.complete.domain.member.oauth.provider.impl.GoogleOAuth2UserInfo;
import org.complete.domain.member.oauth.provider.impl.KakaoOAuth2UserInfo;
import org.complete.domain.member.oauth.provider.impl.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        return switch (providerType) {
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);
            case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
            case NAVER -> new NaverOAuth2UserInfo(attributes);
        };
    }
}

