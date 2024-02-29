package org.complete.challang.app.account.oauth2.userinfo;

import org.complete.challang.app.account.oauth2.userinfo.impl.NaverOAuth2UserInfo;
import org.complete.challang.app.account.user.domain.entity.SocialType;
import org.complete.challang.app.account.oauth2.userinfo.impl.GoogleOAuth2UserInfo;
import org.complete.challang.app.account.oauth2.userinfo.impl.KakaoOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(SocialType socialType, Map<String, Object> attributes) {
        return switch (socialType) {
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);
            case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
            case NAVER -> new NaverOAuth2UserInfo(attributes);
        };
    }
}

