package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.evento;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Deporte;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_tipo_evento")
public class TipoEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private Integer puntosNegativos;

    @ManyToOne
    @JoinColumn(name = "id_deporte")
    private Deporte deporte;

    public TipoEvento() {
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

    public Integer getPuntosNegativos() {
        return puntosNegativos;
    }

    public void setPuntosNegativos(Integer puntosNegativos) {
        this.puntosNegativos = puntosNegativos;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }
}
