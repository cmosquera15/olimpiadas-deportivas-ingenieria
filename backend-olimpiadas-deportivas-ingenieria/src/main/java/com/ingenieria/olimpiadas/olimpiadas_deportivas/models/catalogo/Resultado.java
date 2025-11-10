package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_resultado")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Resultado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, length=255)
    private String nombre;
}
