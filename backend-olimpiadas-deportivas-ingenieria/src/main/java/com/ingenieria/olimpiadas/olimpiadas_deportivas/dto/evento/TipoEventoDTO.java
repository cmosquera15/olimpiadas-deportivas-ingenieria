package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.evento;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.evento.TipoEvento;

public record TipoEventoDTO(
    Integer id,
    String nombre,
    Integer puntosNegativos,
    Integer deporteId
) {
    public TipoEventoDTO(TipoEvento te) {
        this(
            te.getId(),
            te.getNombre(),
            te.getPuntosNegativos(),
            te.getDeporte() != null ? te.getDeporte().getId() : null
        );
    }
}
