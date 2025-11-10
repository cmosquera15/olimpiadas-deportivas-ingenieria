package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo;

import java.util.List;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo.EquipoPosicionDTO;

public record TablaPosicionesDTO(
        Integer torneoId,
        String torneoNombre,
        Integer grupoId,
        String grupoNombre,
        List<EquipoPosicionDTO> posiciones
) {}
