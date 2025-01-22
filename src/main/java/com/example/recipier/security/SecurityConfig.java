package com.example.recipier.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((AbstractHttpConfigurer::disable))
                .authorizeHttpRequests
                        (auth -> auth
                                .requestMatchers("/api/recipes/external/recipes").permitAll()
                                .requestMatchers("/api/auth/**").permitAll() // Rejestracja i logowanie dostępne dla wszystkich
                                .requestMatchers("/api/auth/users/**").hasRole("ADMIN") // Endpoint zmiany ról tylko dla ADMIN
                                .requestMatchers("/api/recipes/**").permitAll() // Endpointy przepisów dostepne dla wszystkich
                                .anyRequest().authenticated()
                        )
                .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}