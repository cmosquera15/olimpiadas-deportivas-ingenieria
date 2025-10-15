package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Fase;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Grupo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Jornada;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Lugar;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_partido")
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate fecha;
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name = "id_lugar")
    private Lugar lugar;

    @ManyToOne
    @JoinColumn(name = "id_jornada")
    private Jornada jornada;

    @ManyToOne
    @JoinColumn(name = "id_fase")
    private Fase fase;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "id_usuario_arbitro")
    private Usuario usuarioArbitro;

    @ManyToOne
    @JoinColumn(name = "id_torneo")
    private Torneo torneo;

    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipoPartido> equipos;

    public Partido() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Usuario getUsuarioArbitro() {
        return usuarioArbitro;
    }

    public void setUsuarioArbitro(Usuario usuarioArbitro) {
        this.usuarioArbitro = usuarioArbitro;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<EquipoPartido> getEquipos() {
        return equipos;
    }
    
    public void setEquipos(List<EquipoPartido> equipos) {
        this.equipos = equipos;
    }
}
