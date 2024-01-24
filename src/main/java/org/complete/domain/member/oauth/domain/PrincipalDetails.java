package org.complete.domain.member.oauth.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.complete.domain.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrincipalDetails implements UserDetails, OAuth2User {

    private String socialId;
    private ProviderType providerType;
    private RoleType roleType;
    private Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getName() {
        return socialId;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public static PrincipalDetails from(String socialId){
        return PrincipalDetails.builder()
                .socialId(socialId)
                .build();
    }
}
