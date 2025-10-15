package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Partido;

public record PartidoDTO(
    LocalDate fecha,
    LocalTime hora,
    String lugar,
    String fase,
    String arbitro,
    String observaciones,
    List<EquipoPartidoDTO> equipos
) {
    public PartidoDTO(Partido p) {
        this(
            p.getFecha(),
            p.getHora(),
            p.getLugar() != null ? p.getLugar().getNombre() : null,
            p.getFase() != null ? p.getFase().getNombre() : null,
            p.getUsuarioArbitro() != null ? p.getUsuarioArbitro().getNombre() : null,
            p.getObservaciones(),
            p.getEquipos().stream().map(EquipoPartidoDTO::new).toList()
        );
    }
}
