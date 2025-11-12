package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.olimpiada;

public record TorneoSummaryDTO(
    Integer id,
    String nombre,
    Integer deporteId,
    String deporteNombre
) {}
