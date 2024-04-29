package org.complete.challang.app.account.oauth2;

import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.account.user.domain.entity.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User, UserDetails {

    private final Long userId;
    private final String email;
    private final RoleType roleType;
    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;

    @Builder
    public CustomOAuth2User(Long userId,
                            String email,
                            RoleType roleType,
                            Map<String, Object> attributes,
                            Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.email = email;
        this.roleType = roleType;
        this.attributes = attributes;
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId);
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

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return null;
    }
}
