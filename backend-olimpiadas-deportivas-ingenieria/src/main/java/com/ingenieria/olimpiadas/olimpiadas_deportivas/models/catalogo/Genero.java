package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_genero")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Genero {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, length=50)
    private String nombre;
}
