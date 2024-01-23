package org.complete.domain.member.oauth.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetails implements OAuth2User {

    private String socialId;
    private ProviderType providerType;
    private RoleType roleType;
    private Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getName() {
        return socialId;
    }
}
