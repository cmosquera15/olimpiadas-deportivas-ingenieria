package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.common.IdNombreDTO;

public record EquipoListViewDTO(
        Integer id,
        String nombre,
        IdNombreDTO torneo,
        IdNombreDTO grupo
) {}
