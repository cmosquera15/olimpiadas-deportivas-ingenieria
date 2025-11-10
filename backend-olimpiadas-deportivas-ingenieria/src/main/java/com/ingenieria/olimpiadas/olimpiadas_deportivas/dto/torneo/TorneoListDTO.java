package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo;

public record TorneoListDTO(
        Integer id,
        String nombre,
        Integer anio,
        Integer id_deporte,
        Boolean activo
) {}
