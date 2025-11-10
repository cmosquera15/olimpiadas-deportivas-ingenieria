package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.partido;

import jakarta.validation.constraints.NotNull;

public record AsignarEquiposRequest(
        @NotNull Integer equipoId1,
        @NotNull Integer equipoId2
) {}
