package org.complete.domain.member.oauth.domain;

public enum ProviderType {

    GOOGLE("google", "구글 로그인"),
    KAKAO("kakao", "카카오 로그인"),
    NAVER("naver", "네이버 로그인");

    final String providerId;
    final String description;

    ProviderType(String providerId, String description) {
        this.providerId = providerId;
        this.description = description;
    }
}
