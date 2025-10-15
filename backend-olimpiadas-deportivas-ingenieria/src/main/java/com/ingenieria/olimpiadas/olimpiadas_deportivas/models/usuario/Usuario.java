package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.EntidadPromotoraSalud;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.ProgramaAcademico;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String documento;
    private String correo;

    @ManyToOne
    @JoinColumn(name = "id_programa_academico")
    private ProgramaAcademico programaAcademico;

    @ManyToOne
    @JoinColumn(name = "id_eps")
    private EntidadPromotoraSalud entidadPromotoraSalud;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public ProgramaAcademico getProgramaAcademico() {
        return programaAcademico;
    }

    public void setProgramaAcademico(ProgramaAcademico programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    public EntidadPromotoraSalud getEntidadPromotoraSalud() {
        return entidadPromotoraSalud;
    }

    public void setEntidadPromotoraSalud(EntidadPromotoraSalud entidadPromotoraSalud) {
        this.entidadPromotoraSalud = entidadPromotoraSalud;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    
}
