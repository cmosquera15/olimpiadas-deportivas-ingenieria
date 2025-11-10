package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.partido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class PartidoSetScoreDTO {

    @NotNull(message = "equipo_local_id es obligatorio")
    private Integer equipo_local_id;

    @NotNull(message = "equipo_visitante_id es obligatorio")
    private Integer equipo_visitante_id;

    @NotNull @PositiveOrZero
    private Integer puntos_local;

    @NotNull @PositiveOrZero
    private Integer puntos_visitante;

    public Integer getEquipo_local_id() { return equipo_local_id; }
    public void setEquipo_local_id(Integer equipo_local_id) { this.equipo_local_id = equipo_local_id; }
    public Integer getEquipo_visitante_id() { return equipo_visitante_id; }
    public void setEquipo_visitante_id(Integer equipo_visitante_id) { this.equipo_visitante_id = equipo_visitante_id; }
    public Integer getPuntos_local() { return puntos_local; }
    public void setPuntos_local(Integer puntos_local) { this.puntos_local = puntos_local; }
    public Integer getPuntos_visitante() { return puntos_visitante; }
    public void setPuntos_visitante(Integer puntos_visitante) { this.puntos_visitante = puntos_visitante; }
}
