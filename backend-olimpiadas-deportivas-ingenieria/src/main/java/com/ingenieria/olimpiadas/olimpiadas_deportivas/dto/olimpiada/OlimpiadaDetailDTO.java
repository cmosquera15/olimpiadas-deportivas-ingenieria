package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.olimpiada;

import java.util.List;

public record OlimpiadaDetailDTO(
    Integer id,
    String nombre,
    String slug,
    Short edicion,
    Short anio,
    Boolean activo,
    List<TorneoSummaryDTO> torneos
) {}

