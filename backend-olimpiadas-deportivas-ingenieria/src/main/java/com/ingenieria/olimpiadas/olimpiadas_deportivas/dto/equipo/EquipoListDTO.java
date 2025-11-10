package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo;

public record EquipoListDTO(
        Integer id,
        String nombre,
        Integer id_torneo,
        Integer id_grupo,
        Integer id_programa_academico_1,
        Integer id_programa_academico_2,
        Integer id_usuario_capitan
) {}
