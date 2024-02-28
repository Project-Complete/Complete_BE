package org.complete.challang.app.account.oauth2;

import lombok.Builder;
import lombok.Getter;
import org.complete.challang.app.account.user.domain.entity.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private String email;
    private RoleType roleType;

    @Builder
    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes,
                            String nameAttributeKey,
                            String email, RoleType roleType) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.roleType = roleType;
    }
}
