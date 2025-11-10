package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_eps")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EntidadPromotoraSalud {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, length=255)
    private String nombre;
}
