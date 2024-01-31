package org.complete.challang.account.user.oauth2.userinfo;

import org.complete.challang.account.user.domain.entity.SocialType;
import org.complete.challang.account.user.domain.entity.User;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getSocialId();

    public abstract String getNickname();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public User toEntity(SocialType socialType) {
        return User.builder()
                .socialId(getSocialId())
                .email(getEmail())
                .profileImageUrl(getImageUrl())
                .socialType(socialType)
                .build();
    }
}
