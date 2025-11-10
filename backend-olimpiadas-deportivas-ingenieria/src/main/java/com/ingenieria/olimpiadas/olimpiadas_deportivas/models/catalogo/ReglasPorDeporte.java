package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_reglas_por_deporte")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReglasPorDeporte {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="requiere_mujer_en_plantilla", nullable=false)
    private Boolean requiereMujerEnPlantilla;

    @Column(name="min_jugadores_equipo", nullable=false)
    private Integer minJugadoresEquipo;

    @ManyToOne
    @JoinColumn(name="id_deporte", nullable=false)
    private Deporte deporte;
}
