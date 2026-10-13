package com.dosw.bluevelvet.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.dosw.bluevelvet.security.JwtAuthFilter;
import com.dosw.bluevelvet.security.RestAccessDeniedHandler;
import com.dosw.bluevelvet.security.RestAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

/**
 * Reglas de acceso por endpoint. Cada regla corresponde a una fila de la
 * tabla de roles y permisos del README.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String ADMIN = "ADMIN";
    private static final String CHEF = "CHEF";
    private static final String MESERO = "MESERO";
    private static final String CLIENTE = "CLIENTE";

    private final JwtAuthFilter jwtAuthFilter;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final RestAccessDeniedHandler accessDeniedHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp.policyDirectives(
                                "default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; img-src 'self' data:"))
                        .frameOptions(frame -> frame.deny())
                        .xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)))
                .authorizeHttpRequests(auth -> auth
                        // Publicos
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login", "/api/v1/auth/registro").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/menu").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/error").permitAll()
                        // Platos
                        .requestMatchers(HttpMethod.GET, "/api/v1/platos/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/platos").hasAnyRole(CHEF, ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/platos/**").hasRole(ADMIN)
                        // Mesas
                        .requestMatchers(HttpMethod.GET, "/api/v1/mesas/**").hasAnyRole(MESERO, ADMIN)
                        .requestMatchers("/api/v1/mesas/**").hasRole(ADMIN)
                        // Reservas
                        .requestMatchers(HttpMethod.POST, "/api/v1/reservas").hasAnyRole(CLIENTE, MESERO, ADMIN)
                        .requestMatchers("/api/v1/reservas/**").hasAnyRole(MESERO, ADMIN)
                        // Pedidos
                        .requestMatchers(HttpMethod.GET, "/api/v1/pedidos/**").hasAnyRole(MESERO, CHEF, ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/v1/pedidos").hasAnyRole(MESERO, ADMIN)
                        // Cuentas y parqueadero
                        .requestMatchers("/api/v1/cuentas/**", "/api/v1/vehiculos/**").hasAnyRole(MESERO, ADMIN)
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Evita que Spring Boot registre el filtro JWT dos veces (fuera y dentro de la cadena de seguridad)
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilterRegistration(JwtAuthFilter filter) {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
