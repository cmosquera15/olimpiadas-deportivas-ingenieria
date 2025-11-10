package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_permiso")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Permiso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, length=255)
    private String nombre;

    @Column(nullable=false, length=255)
    private String descripcion;
}
