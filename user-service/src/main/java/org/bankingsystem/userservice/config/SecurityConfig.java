package org.bankingsystem.userservice.config;

import lombok.RequiredArgsConstructor;
import org.bankingsystem.userservice.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Central Spring Security configuration.
 *
 * @Configuration tells Spring this class defines beans. Any method annotated
 * with @Bean produces an object that Spring stores in its "application context"
 * (the container of all managed objects) and injects wherever it's needed.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Exposes a PasswordEncoder bean. Because it lives in the context, any other
     * bean (e.g. UserServiceImpl) can have it injected via its constructor.
     *
     * BCrypt hashes are one-way: you can't reverse them. To verify a login later
     * you'll call passwordEncoder.matches(rawPassword, storedHash).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines WHICH requests are allowed through and wires in the JWT filter.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF protection is meant for browser-form apps; disable it for a
                // stateless REST API (we authenticate each request via the token).
                .csrf(AbstractHttpConfigurer::disable)
                // No server-side sessions: every request must carry its own JWT.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public: login and user registration.
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        // Everything else needs a valid JWT.
                        .anyRequest().authenticated()
                )
                // Run our JWT check before Spring's username/password filter.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
