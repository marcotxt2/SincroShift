package com.sincroshift.api.config;

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
    public OpenAPI customOpenAPI() {
        // Define o esquema de segurança (Bearer Token)
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info().title("SincroShift API").version("1.0.0"))
                // Adiciona o esquema aos componentes do Swagger
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                // Aplica a exigência de segurança globalmente
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}