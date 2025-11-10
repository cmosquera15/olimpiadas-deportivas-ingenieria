package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo;

import jakarta.validation.constraints.*;

public class EquipoCreateDTO {

    @Size(max = 255, message = "Nombre muy largo")
    private String nombre;

    @NotNull(message = "id_torneo es obligatorio")
    private Integer id_torneo;

    @NotNull(message = "id_grupo es obligatorio")
    private Integer id_grupo;

    @NotNull(message = "id_programa_academico_1 es obligatorio")
    private Integer id_programa_academico_1;

    private Integer id_programa_academico_2;

    private Integer id_usuario_capitan;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getId_torneo() { return id_torneo; }
    public void setId_torneo(Integer id_torneo) { this.id_torneo = id_torneo; }
    public Integer getId_grupo() { return id_grupo; }
    public void setId_grupo(Integer id_grupo) { this.id_grupo = id_grupo; }
    public Integer getId_programa_academico_1() { return id_programa_academico_1; }
    public void setId_programa_academico_1(Integer id_programa_academico_1) { this.id_programa_academico_1 = id_programa_academico_1; }
    public Integer getId_programa_academico_2() { return id_programa_academico_2; }
    public void setId_programa_academico_2(Integer id_programa_academico_2) { this.id_programa_academico_2 = id_programa_academico_2; }
    public Integer getId_usuario_capitan() { return id_usuario_capitan; }
    public void setId_usuario_capitan(Integer id_usuario_capitan) { this.id_usuario_capitan = id_usuario_capitan; }
}
