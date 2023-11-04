package com.example.photographer.config;

import com.example.photographer.support.ContactType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    private static final String ADMIN_API = "/admin/**";
    private static final String WEB_API = "/api/**";

    @Bean
    public GroupedOpenApi webOpenApi() {
        return GroupedOpenApi.builder()
                .group("web")
                .displayName("Пользователь")
                .pathsToMatch(WEB_API)
                .build();
    }

    @Bean
    public GroupedOpenApi adminOpenApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .displayName("Админ")
                .pathsToMatch(ADMIN_API)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSchemas(
                                "ContactTypes", contactTypes()
                        ))
                .info(new Info().title("Test")
                        .description("Test Description")
                        .version("1.0.0"));
    }

    private MapSchema contactTypes() {
        MapSchema contactMap = new MapSchema();
        Arrays.stream(ContactType.values()).forEach(
                contactType -> contactMap.addProperty(contactType.name().toLowerCase(), new StringSchema())
        );

        return contactMap;
    }
}
