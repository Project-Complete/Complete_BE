package org.complete.domain.member.oauth.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.complete.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public PrincipalDetails(String socialId,
                            ProviderType providerType,
                            RoleType roleType,
                            Collection<GrantedAuthority> authorities,
                            Map<String, Object> attributes) {
        this.socialId = socialId;
        this.providerType = providerType;
        this.roleType = roleType;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static PrincipalDetails from(Member member, Map<String, Object> attributes){
        return PrincipalDetails.builder()
                .socialId(member.getSocialId())
                .providerType(member.getProviderType())
                .roleType(member.getRoleType())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode())))
                .attributes(attributes)
                .build();
    }
}
