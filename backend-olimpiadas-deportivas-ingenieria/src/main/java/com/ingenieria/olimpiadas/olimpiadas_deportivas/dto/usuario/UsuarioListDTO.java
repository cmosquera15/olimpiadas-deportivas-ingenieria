package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.usuario;

public record UsuarioListDTO(
        Integer id,
        String nombre,
        String correo,
        String rol,
        Boolean habilitado
) {}
