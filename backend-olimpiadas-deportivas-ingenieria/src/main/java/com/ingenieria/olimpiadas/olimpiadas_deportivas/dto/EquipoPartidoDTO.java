package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.EquipoPartido;

public record EquipoPartidoDTO(
    String nombre,
    Integer puntos,
    String resultado
) {
    public EquipoPartidoDTO(EquipoPartido ep) {
        this(
            ep.getEquipo().getNombre(), 
            ep.getPuntos(), 
            ep.getResultado() != null ? ep.getResultado().getNombre() : null);
    }
}
