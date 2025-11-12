package com.ingenieria.olimpiadas.olimpiadas_deportivas.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("🔍 JWT Filter: " + method + " " + requestUri);

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = resolveToken(header);

        System.out.println("🔍 Token present: " + (token != null));

        if (token != null && jwtTokenProvider.validateToken(token)) {
            System.out.println("🔍 Token valid, setting authentication");
            String email = jwtTokenProvider.getEmail(token);
            List<SimpleGrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            System.out.println("🔍 User: " + email + ", Authorities: " + authorities);

            AbstractAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);

            org.springframework.security.core.context.SecurityContextHolder
                    .getContext().setAuthentication(auth);
        } else if (token != null) {
            System.out.println("🔍 Token invalid or expired");
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(String header) {
        if (!StringUtils.hasText(header)) return null;
        String h = header.trim();
        if ("undefined".equalsIgnoreCase(h) || "null".equalsIgnoreCase(h)) return null;
        if (!h.startsWith("Bearer ")) return null;
        String raw = h.substring(7).trim();
        if (!StringUtils.hasText(raw)) return null;
        if ("undefined".equalsIgnoreCase(raw) || "null".equalsIgnoreCase(raw)) return null;
        return raw;
    }
}
