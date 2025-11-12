package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.usuario;

public record CandidatoJugadorDTO(
    Integer id,
    String nombre,
    String correo,
    String documento,
    Integer programaId,
    String programaNombre,
    String generoNombre
) {}