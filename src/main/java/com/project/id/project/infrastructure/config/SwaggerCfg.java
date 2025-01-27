package com.project.id.project.infrastructure.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;

// @Configuration
// public class SwaggerCfg {
//     // @Bean
//     // public OpenAPI api() {
//     //     return new OpenAPI()
//     //         .servers(List.of(new Server().url("http://localhost:8081")))
//     //         .info(new Info().title("Project API"));
//     // }
//     // @Bean
//     // public OpenAPI api() {
//     //     return new OpenAPI()
//     //         .servers(List.of(new Server().url("http://localhost:8081")))
//     //         .info(new Info().title("Project API"))
//     //         .components(new Components()
//     //             .addSecuritySchemes("JWT", new SecurityScheme()
//     //                 .type(SecurityScheme.Type.HTTP)
//     //                 .scheme("bearer")
//     //                 .bearerFormat("JWT")
//     //         ))
//     //         .addSecurityItem(new SecurityRequirement().addList("JWT"));
//     // }

//     @Bean
//     public OpenAPI api() {
//         return new OpenAPI()
//             .servers(List.of(new Server().url("http://localhost:8081")))
//             .info(new Info().title("Project API"))
//             .components(new Components()
//                 .addSecuritySchemes("cookieAuth", new SecurityScheme()
//                     .type(SecurityScheme.Type.APIKEY)
//                     .in(SecurityScheme.In.COOKIE)
//                     .name("flh")
//             ))
//             .addSecurityItem(new SecurityRequirement().addList("cookieAuth"));
//     }
// }


// @Configuration
// public class SwaggerCfg {

//     @Bean
//     public OpenAPI api() {
//         return new OpenAPI()
//             .servers(List.of(new Server().url("http://localhost:8081")))
//             .info(new Info().title("Project API"))
//             .components(new Components()
//                 .addSecuritySchemes("cookieAuth", new SecurityScheme()
//                     .type(SecurityScheme.Type.APIKEY)
//                     .in(SecurityScheme.In.COOKIE)
//                     .name("flh")
//                 )
//                 .addSecuritySchemes("JWT", new SecurityScheme()
//                     .type(SecurityScheme.Type.HTTP)
//                     .scheme("bearer")
//                     .bearerFormat("JWT")
//                 )
//             )
//             .addSecurityItem(new SecurityRequirement().addList("cookieAuth"))
//             .addSecurityItem(new SecurityRequirement().addList("JWT"));
//     }
    
// }



@Configuration
public class SwaggerCfg {

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String authServerUrl;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    private static final String OAUTH_SCHEME_NAME = "keycloak_oauth2_scheme";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
            .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME))
            .info(new Info().title("My API")
                .description("API with OAuth2 and Keycloak authentication")
                .version("1.0"));
    }

    private SecurityScheme createOAuthScheme() {
        OAuthFlows flows = new OAuthFlows().implicit(createAuthorizationFlow());
        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(flows);
    }

    private OAuthFlow createAuthorizationFlow() {
        return new OAuthFlow()
            .authorizationUrl(authServerUrl + "/protocol/openid-connect/auth")
            .tokenUrl(authServerUrl + "/protocol/openid-connect/token")
            .scopes(new io.swagger.v3.oas.models.security.Scopes()
                .addString("openid", "OpenID Authentication"));
    }
    
}
