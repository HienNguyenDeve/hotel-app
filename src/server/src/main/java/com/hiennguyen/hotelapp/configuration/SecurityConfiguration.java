package com.hiennguyen.hotelapp.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        // Allow 4200 and 3000 ports for development
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.addAllowedHeader("*"); // X-Requested-With, Content-Type, Authorization, Origin, Accept
        config.addAllowedMethod("*"); // GET, POST, PUT, DELETE, PATCH, OPTIONS
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/v1/rooms/**").anonymous()
                        .anyRequest().anonymous())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
