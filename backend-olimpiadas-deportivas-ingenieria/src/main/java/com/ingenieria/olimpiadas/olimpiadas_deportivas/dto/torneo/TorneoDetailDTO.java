package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo;

public record TorneoDetailDTO(
        Integer id,
        String nombre,
        Integer idDeporte,
        String deporteNombre,
        Integer idOlimpiada,
        String olimpiadaNombre,
        String reglamentoUrl
) {}
