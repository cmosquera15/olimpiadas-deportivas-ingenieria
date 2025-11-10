package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.partido;

import java.time.LocalDate;
import java.time.LocalTime;

public record PartidoDetailDTO(
        Integer id,
        LocalDate fecha,
        LocalTime hora,
        Integer id_torneo,
        String torneoNombre,
        Integer id_lugar,
        String lugarNombre,
        Integer id_fase,
        String faseNombre,
        Integer id_grupo,
        String grupoNombre,
        Integer id_jornada,
        Integer numeroJornada,
        Integer id_usuario_arbitro,
        String arbitroNombre,
        Integer equipoLocalId,
        String equipoLocalNombre,
        Integer equipoLocalPuntos,
        Integer equipoVisitanteId,
        String equipoVisitanteNombre,
        Integer equipoVisitantePuntos
) {}
