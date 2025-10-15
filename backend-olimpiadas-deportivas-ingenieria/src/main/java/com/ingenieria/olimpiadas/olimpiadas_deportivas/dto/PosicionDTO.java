package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto;

public record PosicionDTO(
    String equipo,
    Integer partidosJugados,
    Integer ganados,
    Integer perdidos,
    Integer empatados,
    Integer puntos
) {}
