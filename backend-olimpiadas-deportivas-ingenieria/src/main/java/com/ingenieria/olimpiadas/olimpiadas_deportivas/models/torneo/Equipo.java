package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Deporte;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Grupo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.ProgramaAcademico;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_equipo")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_programa_academico_1")
    private ProgramaAcademico programaAcademico1;

    @ManyToOne
    @JoinColumn(name = "id_programa_academico_2")
    private ProgramaAcademico programaAcademico2;

    @ManyToOne
    @JoinColumn(name = "id_deporte")
    private Deporte deporte;

    @ManyToOne
    @JoinColumn(name = "id_usuario_capitan")
    private Usuario usuarioCapitan;
    
    @ManyToOne
    @JoinColumn(name = "id_torneo")
    private Torneo torneo;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    public Equipo() {
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

    public ProgramaAcademico getProgramaAcademico1() {
        return programaAcademico1;
    }

    public void setProgramaAcademico1(ProgramaAcademico programaAcademico1) {
        this.programaAcademico1 = programaAcademico1;
    }

    public ProgramaAcademico getProgramaAcademico2() {
        return programaAcademico2;
    }

    public void setProgramaAcademico2(ProgramaAcademico programaAcademico2) {
        this.programaAcademico2 = programaAcademico2;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public Usuario getUsuarioCapitan() {
        return usuarioCapitan;
    }

    public void setUsuarioCapitan(Usuario usuarioCapitan) {
        this.usuarioCapitan = usuarioCapitan;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}
