package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo;

public record EquipoDetailDTO(
        Integer id,
        String nombre,
        Integer id_torneo,
        String torneoNombre,
        Integer id_grupo,
        String grupoNombre,
        Integer id_programa_academico_1,
        String programa1Nombre,
        Integer id_programa_academico_2,
        String programa2Nombre,
        Integer id_usuario_capitan,
        String capitanNombre
) {}
