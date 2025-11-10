package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo;

public record PosicionDTO(
    Integer equipoId, String equipoNombre,
    Integer pj, Integer pg, Integer pe, Integer pp, Integer wo,
    Integer gf, Integer gc, Integer dg, Integer pts,
    Double  fairPlayProm
) {}
