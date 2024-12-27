package com.example.userapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    /**
     * Configures and provides the OpenAPI documentation for the User Management API.
     * This method sets up the basic information for the API documentation, including
     * the title, version, and a brief description of the application.
     *
     * @return An OpenAPI object containing the configured API information.
     **/
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management API")
                        .version("1.0.0")
                        .description("API documentation for the User Management application.")
                );
    }
}
