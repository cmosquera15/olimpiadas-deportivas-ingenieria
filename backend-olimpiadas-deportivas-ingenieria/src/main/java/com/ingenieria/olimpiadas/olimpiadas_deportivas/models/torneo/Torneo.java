package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Deporte;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_torneo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Torneo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, length=255)
    private String nombre;

    @Column(nullable=false)
    private Integer anio;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_deporte", nullable=false)
    private Deporte deporte;

    @Builder.Default
    @Column(nullable=false)
    private Boolean activo = Boolean.TRUE;
}
