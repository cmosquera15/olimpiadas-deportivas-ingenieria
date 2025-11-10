package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo;

public record EquipoPosicionDTO(
        Integer equipoId,
        String equipoNombre,
        Integer pj,
        Integer pg,
        Integer pe,
        Integer pp,
        Integer gf,
        Integer gc,
        Integer gd,
        Integer pts,
        Double fairPlay
) {}
