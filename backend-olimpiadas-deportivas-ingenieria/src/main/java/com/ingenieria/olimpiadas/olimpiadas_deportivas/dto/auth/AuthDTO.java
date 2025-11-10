package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.auth;

public record AuthDTO(
        String token,
        String nombre,
        String correo,
        boolean completo,
        String fotoUrl
) {}
