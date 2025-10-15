package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.evento;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.EquipoPartido;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_equipo_por_partido")
    private EquipoPartido equipoPorPartido;

    @ManyToOne
    @JoinColumn(name = "id_usuario_jugador")
    private Usuario usuarioJugador;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo_evento")
    private TipoEvento tipoEvento;

    public Evento() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EquipoPartido getEquipoPorPartido() {
        return equipoPorPartido;
    }

    public void setEquipoPorPartido(EquipoPartido equipoPorPartido) {
        this.equipoPorPartido = equipoPorPartido;
    }

    public Usuario getUsuarioJugador() {
        return usuarioJugador;
    }

    public void setUsuarioJugador(Usuario usuarioJugador) {
        this.usuarioJugador = usuarioJugador;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }
}
