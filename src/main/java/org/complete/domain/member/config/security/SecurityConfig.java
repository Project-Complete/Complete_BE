package org.complete.domain.member.config.security;

import lombok.RequiredArgsConstructor;
import org.complete.domain.member.config.properties.SecurityProperties;
import org.complete.domain.member.filter.TokenAuthenticationFilter;
import org.complete.domain.member.oauth.handler.OAuth2AuthenticationFailureHandler;
import org.complete.domain.member.oauth.handler.OAuth2AuthenticationSuccessHandler;
import org.complete.domain.member.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import org.complete.domain.member.oauth.service.PrincipalOAuth2UserService;
import org.complete.domain.member.oauth.token.AuthTokenProvider;
import org.complete.domain.member.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityProperties securityProperties;
    private final AuthTokenProvider tokenProvider;
    private final PrincipalOAuth2UserService oAuth2UserService;
    private final MemberRepository memberRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/assets/**", "/h2-console/**", "/h2/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(HttpBasicConfigurer::disable)
                .cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(FormLoginConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(configurer ->
                        configurer
                                .authorizationEndpoint(config ->
                                        config
                                                .baseUri("/oauth2/authorization")
                                                .authorizationRequestRepository(authorizationRequestRepository()))
                                .redirectionEndpoint(config -> config.baseUri("/*/oauth2/code/*"))
                                .userInfoEndpoint(config -> config.userService(oAuth2UserService))
                                .successHandler(oAuth2AuthenticationSuccessHandler())
                                .failureHandler(oAuth2AuthenticationFailureHandler())
                );

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(securityProperties, authorizationRequestRepository(), tokenProvider, memberRepository);
    }

    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(authorizationRequestRepository());
    }
}
