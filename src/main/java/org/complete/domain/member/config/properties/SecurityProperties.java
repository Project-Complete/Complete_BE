package org.complete.domain.member.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
public class SecurityProperties {

    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    @Getter
    public static class Auth {
        private String tokenSecret;
        private long tokenExpiry;
        private long refreshTokenExpiry;
    }

    @Getter
    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }
}
