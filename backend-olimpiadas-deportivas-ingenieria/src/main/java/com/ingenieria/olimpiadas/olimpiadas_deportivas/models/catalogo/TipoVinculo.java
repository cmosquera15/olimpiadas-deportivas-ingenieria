package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_tipo_vinculo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoVinculo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, unique=true, length=80)
    private String nombre;
}
