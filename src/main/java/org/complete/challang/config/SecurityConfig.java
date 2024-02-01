package org.complete.challang.config;

import lombok.RequiredArgsConstructor;
import org.complete.challang.account.jwt.filter.TokenAuthenticationFilter;
import org.complete.challang.account.oauth2.handler.OAuth2FailureHandler;
import org.complete.challang.account.oauth2.handler.OAuth2SuccessHandler;
import org.complete.challang.account.oauth2.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/assets/**", "/h2-console/**", "/h2/**", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(HttpBasicConfigurer::disable)
                .cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(FormLoginConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                                .requestMatchers("/assets/**", "/h2-console/**", "/h2/**", "/favicon.ico").permitAll()
                                .anyRequest().permitAll()
                )
                .oauth2Login(configurer ->
                                configurer
                                        .authorizationEndpoint(config ->
                                                config
                                                        .baseUri("/oauth2/authorization"))
//                                                .authorizationRequestRepository(authorizationRequestRepository()))
                                        .redirectionEndpoint(config -> config.baseUri("/*/oauth2/code/*"))
                                        .userInfoEndpoint(config -> config.userService(customOAuth2UserService))
                                        .successHandler(oAuth2SuccessHandler)
                                        .failureHandler(oAuth2FailureHandler)
                );

        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
