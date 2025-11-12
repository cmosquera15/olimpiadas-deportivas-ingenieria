package com.ingenieria.olimpiadas.olimpiadas_deportivas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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

    public static class Google {
        private String clientId;

        public String getClientId() { return clientId; }
        public void setClientId(String clientId) { this.clientId = clientId; }
    }

    public static class Jwt {
        private String secretB64;
        private long expirationMs = 1800000L;
        private String issuer = "olimpiadas-ingenieria";

        public String getSecretB64() { return secretB64; }
        public void setSecretB64(String secretB64) { this.secretB64 = secretB64; }

        public long getExpirationMs() { return expirationMs; }
        public void setExpirationMs(long expirationMs) { this.expirationMs = expirationMs; }

        public String getIssuer() { return issuer; }
        public void setIssuer(String issuer) { this.issuer = issuer; }
    }

    public static class Cors {
        private List<String> allowedOrigins = List.of("http://localhost:5173", "https://olimpiadas.ingenieria.com", "https://olimpiadasingenieria.com");
        private List<String> allowedMethods = List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS");
        private List<String> allowedHeaders = List.of("Authorization", "Content-Type", "Accept");
        private boolean allowCredentials = true;
        private List<String> exposedHeaders = List.of();

        public List<String> getAllowedOrigins() { return allowedOrigins; }
        public void setAllowedOrigins(List<String> allowedOrigins) { this.allowedOrigins = allowedOrigins; }

        public List<String> getAllowedMethods() { return allowedMethods; }
        public void setAllowedMethods(List<String> allowedMethods) { this.allowedMethods = allowedMethods; }

        public List<String> getAllowedHeaders() { return allowedHeaders; }
        public void setAllowedHeaders(List<String> allowedHeaders) { this.allowedHeaders = allowedHeaders; }

        public boolean isAllowCredentials() { return allowCredentials; }
        public void setAllowCredentials(boolean allowCredentials) { this.allowCredentials = allowCredentials; }

        public List<String> getExposedHeaders() { return exposedHeaders; }
        public void setExposedHeaders(List<String> exposedHeaders) { this.exposedHeaders = exposedHeaders; }
    }


    public String getReglamentoUrl() { return reglamentoUrl; }
    public void setReglamentoUrl(String reglamentoUrl) { this.reglamentoUrl = reglamentoUrl; }
}
