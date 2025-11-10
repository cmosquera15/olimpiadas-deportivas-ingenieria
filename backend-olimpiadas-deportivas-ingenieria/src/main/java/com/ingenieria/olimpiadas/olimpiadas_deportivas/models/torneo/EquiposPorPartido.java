package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Resultado;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_equipos_por_partidos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_equipo","id_partido"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EquiposPorPartido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_equipo", nullable=false)
    private Equipo equipo;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_partido", nullable=false)
    private Partido partido;

    @Column
    private Integer puntos;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_resultado")
    private Resultado resultado;
}
