package com.ingenieria.olimpiadas.olimpiadas_deportivas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;

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

        cfg.setAllowedOrigins(new ArrayList<>(appProperties.getCors().getAllowedOrigins()));
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
