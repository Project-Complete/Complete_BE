package org.complete.challang.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String access = "Access Token";
        String refresh = "Refresh Token";
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(access)
                .addList(refresh);

        Components components = new Components()
                .addSecuritySchemes(access, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"))
                .addSecuritySchemes(refresh, new SecurityScheme()
                        .name("Authorization-refresh")
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                );

        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("Challang API Documentation")
                .description("Team Complete의 찰랑 프로젝트 API 문서")
                .version("1.0.0");
    }
}
