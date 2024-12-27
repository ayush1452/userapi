package com.example.userapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for web security settings.
 */
@Configuration
public class WebSecurityConfig {
    /**
     * Creates a PasswordEncoder bean for encoding passwords.
     *
     * @return A BCryptPasswordEncoder instance for secure password hashing.
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain to allow unauthenticated access to all endpoints.
     *
     * @param http The HttpSecurity object to be configured.
     * @return A SecurityFilterChain with the specified security configurations.
     * @throws Exception If an error occurs during the security configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                );
        return http.build();
    }

}
