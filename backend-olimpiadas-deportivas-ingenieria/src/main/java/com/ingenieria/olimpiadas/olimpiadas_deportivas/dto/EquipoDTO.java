package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Equipo;

public record EquipoDTO(
    String nombre,
    String programaAcademico1,
    String programaAcademico2,
    String deporte,
    String capitan,
    String torneo,
    char grupo
) {
    public EquipoDTO(Equipo e) {
        this(
            e.getNombre(),
            e.getProgramaAcademico1() != null ? e.getProgramaAcademico1().getNombre() : null,
            e.getProgramaAcademico2() != null ? e.getProgramaAcademico2().getNombre() : null,
            e.getDeporte() != null ? e.getDeporte().getNombre() : null,
            e.getUsuarioCapitan() != null ? e.getUsuarioCapitan().getNombre() : null,
            e.getTorneo() != null ? e.getTorneo().getNombre() : null,
            e.getGrupo() != null ? e.getGrupo().getNombre() : null);
    }
}
