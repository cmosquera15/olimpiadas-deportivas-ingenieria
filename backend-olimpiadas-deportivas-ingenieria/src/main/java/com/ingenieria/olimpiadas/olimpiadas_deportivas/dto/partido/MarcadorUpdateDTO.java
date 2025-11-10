package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.partido;

import jakarta.validation.constraints.NotNull;

public record MarcadorUpdateDTO(
        @NotNull Integer equipo1Id,
        @NotNull Integer equipo2Id,
        Integer puntosEquipo1,
        Integer puntosEquipo2,
        Integer resultadoEquipo1Id,
        Integer resultadoEquipo2Id
) {}
