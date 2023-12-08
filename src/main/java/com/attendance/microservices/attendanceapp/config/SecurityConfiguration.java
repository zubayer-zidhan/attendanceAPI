package com.attendance.microservices.attendanceapp.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static com.attendance.microservices.attendanceapp.entities.Role.ADMIN;
import static com.attendance.microservices.attendanceapp.entities.Permission.ADMIN_CREATE;
import static com.attendance.microservices.attendanceapp.entities.Permission.ADMIN_DELETE;
import static com.attendance.microservices.attendanceapp.entities.Permission.ADMIN_READ;
import static com.attendance.microservices.attendanceapp.entities.Permission.ADMIN_UPDATE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.DELETE;


import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/v1/auth/**")
                                                .permitAll()
                                                .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                                                .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                                                .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                                                .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                                                .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
                                                // .requestMatchers("/api/v1/auth/register").hasRole(ADMIN.name())
                                                // .requestMatchers(GET, "/api/v1/auth/register").hasAuthority(ADMIN_READ.name())
                                                // .requestMatchers(POST, "/api/v1/auth/register").hasAuthority(ADMIN_CREATE.name())
                                                // .requestMatchers(PUT, "/api/v1/auth/register").hasAuthority(ADMIN_UPDATE.name())
                                                // .requestMatchers(DELETE, "/api/v1/auth/register").hasAuthority(ADMIN_DELETE.name())
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(sess -> sess
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();

        }
}
