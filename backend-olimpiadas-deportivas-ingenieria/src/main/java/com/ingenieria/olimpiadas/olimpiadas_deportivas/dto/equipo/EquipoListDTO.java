package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo;

public record EquipoListDTO(
        Integer id,
        String nombre,
        Integer torneoId,
        String torneoNombre,
        Integer grupoId,
        Integer programaAcademico1Id,
        Integer programaAcademico2Id,
        Integer capitanId,
        Integer integrantesCount
) {}
