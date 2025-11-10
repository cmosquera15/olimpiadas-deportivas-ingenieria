package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo;

public record TorneoDetailDTO(
        Integer id,
        String nombre,
        Integer anio,
        Integer id_deporte,
        Boolean activo,
        String reglamentoUrl
) {}
