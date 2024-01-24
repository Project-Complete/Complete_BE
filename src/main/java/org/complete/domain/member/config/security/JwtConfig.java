package org.complete.domain.member.config.security;

import org.complete.domain.member.oauth.token.AuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String key;

    @Bean
    public AuthTokenProvider authTokenProvider() {
        return new AuthTokenProvider(key);
    }
}
