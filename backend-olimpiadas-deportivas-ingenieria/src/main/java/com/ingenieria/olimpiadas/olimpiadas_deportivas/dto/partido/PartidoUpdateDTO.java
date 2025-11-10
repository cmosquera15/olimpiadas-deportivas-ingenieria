package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.partido;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Size;

public class PartidoUpdateDTO {

    private LocalDate fecha;      
    private LocalTime hora;       
    private Integer id_lugar;     
    private Integer id_jornada;
    private Integer id_fase;      
    private Integer id_grupo;
    private Integer id_usuario_arbitro;

    @Size(max = 255)
    private String observaciones;

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
    public Integer getId_usuario_arbitro() { return id_usuario_arbitro; }
    public void setId_usuario_arbitro(Integer id_usuario_arbitro) { this.id_usuario_arbitro = id_usuario_arbitro; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
