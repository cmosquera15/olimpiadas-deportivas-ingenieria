package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.plantilla;

import jakarta.validation.constraints.NotNull;

public class AgregarJugadorAEquipoRequest {

    @NotNull
    private Integer id_usuario;

    @NotNull
    private Integer id_equipo;

    @NotNull
    private Integer id_torneo;

    public Integer getId_usuario() { return id_usuario; }
    public void setId_usuario(Integer id_usuario) { this.id_usuario = id_usuario; }
    public Integer getId_equipo() { return id_equipo; }
    public void setId_equipo(Integer id_equipo) { this.id_equipo = id_equipo; }
    public Integer getId_torneo() { return id_torneo; }
    public void setId_torneo(Integer id_torneo) { this.id_torneo = id_torneo; }
}
