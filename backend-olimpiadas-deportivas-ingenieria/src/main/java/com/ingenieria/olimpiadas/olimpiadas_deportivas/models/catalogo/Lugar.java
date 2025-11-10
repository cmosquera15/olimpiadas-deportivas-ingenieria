package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_lugar")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Lugar {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, unique=true, length=255)
    private String nombre;
}
