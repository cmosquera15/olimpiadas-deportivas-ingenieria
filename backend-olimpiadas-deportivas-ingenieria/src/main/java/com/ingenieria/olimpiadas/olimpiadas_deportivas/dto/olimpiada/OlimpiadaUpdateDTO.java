package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.olimpiada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OlimpiadaUpdateDTO(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    
    @NotNull(message = "La edición es obligatoria")
    Short edicion,
    
    @NotNull(message = "El año es obligatorio")
    Short anio,
    
    Boolean activo
) {}
