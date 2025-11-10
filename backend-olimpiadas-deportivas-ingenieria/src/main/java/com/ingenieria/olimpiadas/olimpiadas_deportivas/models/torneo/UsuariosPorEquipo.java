package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_usuarios_por_equipo",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"id_usuario", "id_equipo"})
        })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuariosPorEquipo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_usuario", nullable=false)
    private Usuario usuario;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_equipo", nullable=false)
    private Equipo equipo;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_torneo", nullable=false)
    private Torneo torneo;
}
