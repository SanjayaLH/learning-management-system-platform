package com.sanjaya.lms_platform.config;

import com.sanjaya.lms_platform.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Password Encoder Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for all REST API calls
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Authorize requests
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to the registration and login endpoint
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // POST (Create) is only for Teachers
                        .requestMatchers(HttpMethod.POST, "/api/v1/courses/**").hasRole("TEACHER")
                        // GET (View) is for both Teachers and Students
                        .requestMatchers(HttpMethod.GET, "/api/v1/courses/**").hasAnyRole("TEACHER", "STUDENT")
                        //For New Enrollments
                        .requestMatchers(HttpMethod.POST, "/api/v1/enrollments/**").hasRole("STUDENT")
                        //For profile view
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/myprofile").hasAnyRole("TEACHER", "STUDENT")
                        // Require authentication for all other endpoints
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}