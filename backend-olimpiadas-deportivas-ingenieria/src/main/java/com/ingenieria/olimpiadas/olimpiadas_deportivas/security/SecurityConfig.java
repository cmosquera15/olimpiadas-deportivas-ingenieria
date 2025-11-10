package com.ingenieria.olimpiadas.olimpiadas_deportivas.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(jwtSecret, jwtExpirationMs, jwtIssuer);
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        return new JwtAuthFilter(jwtTokenProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthFilter jwtAuthFilter) throws Exception {

        http
            .cors(c -> c.configurationSource(corsConfigurationSource))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**"
                ).permitAll()

                .requestMatchers("/api/auth/**").permitAll()

                .requestMatchers(HttpMethod.GET,
                    "/api/programas/**",
                    "/api/eps/**",
                    "/api/deportes/**",
                    "/api/lugares/**",
                    "/api/fases/**",
                    "/api/jornadas/**",
                    "/api/grupos/**",
                    "/api/torneos/**"
                ).permitAll()

                .requestMatchers("/api/admin/**").hasRole("ADMINISTRADOR")
    
                .requestMatchers(HttpMethod.POST, "/api/partidos/**").hasAuthority("Partidos_Crear")

                .requestMatchers("/api/public/**").permitAll()

                .requestMatchers(HttpMethod.PUT, "/api/partidos/**").hasAuthority("Partidos_Editar")
                .requestMatchers(HttpMethod.PUT, "/api/partidos/*/marcador").hasAuthority("Partidos_Editar")
                .requestMatchers(HttpMethod.DELETE, "/api/partidos/**").hasAuthority("Partidos_Eliminar")
                .requestMatchers("/api/eventos/**").hasAuthority("Partidos_Editar") // crear/eliminar eventos
                .requestMatchers(HttpMethod.GET, "/api/eventos/**").permitAll() // si quieres visibles a todos

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
}
