package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.partido;

import jakarta.validation.constraints.NotNull;

public class PartidoAssignTeamsDTO {

    @NotNull(message = "equipo_local_id es obligatorio")
    private Integer equipo_local_id;

    @NotNull(message = "equipo_visitante_id es obligatorio")
    private Integer equipo_visitante_id;

    public Integer getEquipo_local_id() { return equipo_local_id; }
    public void setEquipo_local_id(Integer equipo_local_id) { this.equipo_local_id = equipo_local_id; }
    public Integer getEquipo_visitante_id() { return equipo_visitante_id; }
    public void setEquipo_visitante_id(Integer equipo_visitante_id) { this.equipo_visitante_id = equipo_visitante_id; }
}
