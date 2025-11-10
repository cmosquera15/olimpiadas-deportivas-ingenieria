package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.partido;

import java.time.LocalDate;
import java.time.LocalTime;

public record PartidoListDTO(
        Integer id,
        LocalDate fecha,
        LocalTime hora,
        Integer id_torneo,
        Integer id_lugar,
        Integer id_fase,
        Integer id_grupo,
        Integer id_jornada,
        Integer id_usuario_arbitro
) {}
