package org.complete.challang.account.user.oauth2.userinfo;

import org.complete.challang.account.user.oauth2.userinfo.impl.GoogleOAuth2UserInfo;
import org.complete.challang.account.user.oauth2.userinfo.impl.KakaoOAuth2UserInfo;
import org.complete.challang.account.user.oauth2.userinfo.impl.NaverOAuth2UserInfo;
import org.complete.domain.member.oauth.domain.ProviderType;

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

