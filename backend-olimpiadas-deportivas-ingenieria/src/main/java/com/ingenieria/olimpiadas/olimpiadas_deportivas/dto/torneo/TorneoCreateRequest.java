package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TorneoCreateRequest(
        @NotBlank String nombre,
        @NotNull Integer idDeporte,
        @NotNull Integer idOlimpiada
) {}
