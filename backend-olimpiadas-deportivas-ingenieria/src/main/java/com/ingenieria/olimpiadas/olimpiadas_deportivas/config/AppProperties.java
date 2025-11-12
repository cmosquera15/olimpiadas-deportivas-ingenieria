package com.ingenieria.olimpiadas.olimpiadas_deportivas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Google google = new Google();
    private final Jwt jwt = new Jwt();
    private final Cors cors = new Cors();
    private String reglamentoUrl;

    public Google getGoogle() { return google; }
    public Jwt getJwt() { return jwt; }
    public Cors getCors() { return cors; }

    public String getReglamentoUrl() { return reglamentoUrl; }
    public void setReglamentoUrl(String reglamentoUrl) { this.reglamentoUrl = reglamentoUrl; }

    // ---------- Subprops ----------

    public static class Google {
        private String clientId;

        public String getClientId() { return clientId; }
        public void setClientId(String clientId) { this.clientId = clientId; }
    }

    public static class Jwt {
        private String secretB64;
        private long expirationMs = 1800000L; // 30 min
        private String issuer = "olimpiadas-ingenieria";

        public String getSecretB64() { return secretB64; }
        public void setSecretB64(String secretB64) { this.secretB64 = secretB64; }

        public long getExpirationMs() { return expirationMs; }
        public void setExpirationMs(long expirationMs) { this.expirationMs = expirationMs; }

        public String getIssuer() { return issuer; }
        public void setIssuer(String issuer) { this.issuer = issuer; }
    }

    public static class Cors {
        /** Orígenes exactos permitidos (útiles para prod/local). */
        private List<String> allowedOrigins = new ArrayList<>(List.of(
            "http://localhost:5173",
            "https://olimpiadas.ingenieria.com",
            "https://olimpiadasingenieria.com",
            "https://www.olimpiadas.ingenieria.com"
        ));

        /**
         * Patrones de origen permitidos (útil para previews de Vercel: https://*.vercel.app).
         * Si esta lista NO está vacía, el CorsConfig usará setAllowedOriginPatterns().
         */
        private List<String> allowedOriginPatterns = new ArrayList<>(List.of(
            "https://*.vercel.app"
        ));

        private List<String> allowedMethods = new ArrayList<>(List.of(
            "GET","POST","PUT","PATCH","DELETE","OPTIONS"
        ));

        /** Usa "*" por simplicidad; si quieres, restrínge a ["Authorization","Content-Type","Accept"]. */
        private List<String> allowedHeaders = new ArrayList<>(List.of(
            "*"
        ));

        private boolean allowCredentials = true;

        /** Headers expuestos al cliente (vacío por defecto). */
        private List<String> exposedHeaders = new ArrayList<>();

        // Getters/Setters

        public List<String> getAllowedOrigins() { return allowedOrigins; }
        public void setAllowedOrigins(List<String> allowedOrigins) {
            this.allowedOrigins = (allowedOrigins != null) ? new ArrayList<>(allowedOrigins) : new ArrayList<>();
        }

        public List<String> getAllowedOriginPatterns() { return allowedOriginPatterns; }
        public void setAllowedOriginPatterns(List<String> allowedOriginPatterns) {
            this.allowedOriginPatterns = (allowedOriginPatterns != null) ? new ArrayList<>(allowedOriginPatterns) : new ArrayList<>();
        }

        public List<String> getAllowedMethods() { return allowedMethods; }
        public void setAllowedMethods(List<String> allowedMethods) {
            this.allowedMethods = (allowedMethods != null) ? new ArrayList<>(allowedMethods) : new ArrayList<>();
        }

        public List<String> getAllowedHeaders() { return allowedHeaders; }
        public void setAllowedHeaders(List<String> allowedHeaders) {
            this.allowedHeaders = (allowedHeaders != null) ? new ArrayList<>(allowedHeaders) : new ArrayList<>();
        }

        public boolean isAllowCredentials() { return allowCredentials; }
        public void setAllowCredentials(boolean allowCredentials) { this.allowCredentials = allowCredentials; }

        public List<String> getExposedHeaders() { return exposedHeaders; }
        public void setExposedHeaders(List<String> exposedHeaders) {
            this.exposedHeaders = (exposedHeaders != null) ? new ArrayList<>(exposedHeaders) : new ArrayList<>();
        }
    }
}