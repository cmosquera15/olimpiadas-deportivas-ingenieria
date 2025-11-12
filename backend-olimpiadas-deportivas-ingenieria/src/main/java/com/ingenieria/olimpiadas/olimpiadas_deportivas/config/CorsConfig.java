package com.ingenieria.olimpiadas.olimpiadas_deportivas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {

    private final AppProperties appProperties;

    public CorsConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();

        // Usa patrones si están definidos (p.ej. https://*.vercel.app), sino orígenes exactos.
        List<String> patterns = appProperties.getCors().getAllowedOriginPatterns();
        if (patterns != null && !patterns.isEmpty()) {
            cfg.setAllowedOriginPatterns(new ArrayList<>(patterns));
        } else {
            cfg.setAllowedOrigins(new ArrayList<>(appProperties.getCors().getAllowedOrigins()));
        }

        cfg.setAllowedMethods(new ArrayList<>(appProperties.getCors().getAllowedMethods()));
        cfg.setAllowedHeaders(new ArrayList<>(appProperties.getCors().getAllowedHeaders()));
        cfg.setAllowCredentials(appProperties.getCors().isAllowCredentials());

        List<String> exposed = appProperties.getCors().getExposedHeaders();
        if (exposed != null && !exposed.isEmpty()) {
            cfg.setExposedHeaders(new ArrayList<>(exposed));
        }

        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
