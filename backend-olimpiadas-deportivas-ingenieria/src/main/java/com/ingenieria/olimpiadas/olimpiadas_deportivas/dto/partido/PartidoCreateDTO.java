package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.partido;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class PartidoCreateDTO {

    @NotNull(message = "fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "hora es obligatoria")
    private LocalTime hora;

    @NotNull(message = "id_lugar es obligatorio")
    private Integer id_lugar;

    private Integer id_jornada;

    @NotNull(message = "id_fase es obligatorio")
    private Integer id_fase;

    private Integer id_grupo;

    @Size(max = 255)
    private String observaciones;

    @NotNull(message = "id_usuario_arbitro es obligatorio")
    private Integer id_usuario_arbitro;

    @NotNull(message = "id_torneo es obligatorio")
    private Integer id_torneo;

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public Integer getId_lugar() { return id_lugar; }
    public void setId_lugar(Integer id_lugar) { this.id_lugar = id_lugar; }
    public Integer getId_jornada() { return id_jornada; }
    public void setId_jornada(Integer id_jornada) { this.id_jornada = id_jornada; }
    public Integer getId_fase() { return id_fase; }
    public void setId_fase(Integer id_fase) { this.id_fase = id_fase; }
    public Integer getId_grupo() { return id_grupo; }
    public void setId_grupo(Integer id_grupo) { this.id_grupo = id_grupo; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Integer getId_usuario_arbitro() { return id_usuario_arbitro; }
    public void setId_usuario_arbitro(Integer id_usuario_arbitro) { this.id_usuario_arbitro = id_usuario_arbitro; }
    public Integer getId_torneo() { return id_torneo; }
    public void setId_torneo(Integer id_torneo) { this.id_torneo = id_torneo; }
}
