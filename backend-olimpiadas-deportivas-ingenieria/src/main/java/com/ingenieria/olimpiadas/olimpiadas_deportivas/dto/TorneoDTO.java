package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo;

public record TorneoDTO(
    String nombre,
    String año,
    String deporte,
    String activo
) {
    public TorneoDTO(Torneo t) {
        this(
            t.getNombre(),
            t.getAnio() != null ? t.getAnio().toString() : null,
            t.getDeporte() != null ? t.getDeporte().getNombre() : null,
            t.getActivo() != null ? String.valueOf(t.getActivo().booleanValue()) : null);
    }

}
