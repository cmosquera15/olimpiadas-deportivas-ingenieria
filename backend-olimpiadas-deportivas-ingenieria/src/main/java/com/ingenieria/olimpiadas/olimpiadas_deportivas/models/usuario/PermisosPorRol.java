package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_permisos_por_rol")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PermisosPorRol {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_rol", nullable=false)
    private Rol rol;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_permiso", nullable=false)
    private Permiso permiso;
}
