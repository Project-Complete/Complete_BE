package org.complete.challang.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

        Server devServer = new Server();
        devServer.setUrl("https://dev-backend.challang.com");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");

        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components)
                .servers(List.of(devServer, localServer));
    }

    private Info apiInfo() {
        return new Info()
                .title("Challang API Documentation")
                .description("Team Complete의 찰랑 프로젝트 API 문서")
                .version("1.0.0");
    }
}
