package com.skylena.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Disable CSRF for API
            .authorizeRequests()
            .requestMatchers("/api/auth/**").permitAll()  // Use requestMatchers instead of antMatchers
            .anyRequest().authenticated();  // Protect other endpoints

        return http.build();
    }
}
