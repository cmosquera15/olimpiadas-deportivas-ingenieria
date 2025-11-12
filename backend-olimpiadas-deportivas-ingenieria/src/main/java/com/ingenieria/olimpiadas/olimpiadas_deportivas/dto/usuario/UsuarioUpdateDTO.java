package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.usuario;

public class UsuarioUpdateDTO {
    private String nombre;
    private String documento; // solo si no estaba antes
    private Integer id_programa_academico;
    private Integer id_genero;
    private Integer id_eps;
    private Integer id_tipo_vinculo;
    private String fotoUrl;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public Integer getId_programa_academico() { return id_programa_academico; }
    public void setId_programa_academico(Integer id_programa_academico) { this.id_programa_academico = id_programa_academico; }
    public Integer getId_genero() { return id_genero; }
    public void setId_genero(Integer id_genero) { this.id_genero = id_genero; }
    public Integer getId_eps() { return id_eps; }
    public void setId_eps(Integer id_eps) { this.id_eps = id_eps; }
    public Integer getId_tipo_vinculo() { return id_tipo_vinculo; }
    public void setId_tipo_vinculo(Integer id_tipo_vinculo) { this.id_tipo_vinculo = id_tipo_vinculo; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
}
