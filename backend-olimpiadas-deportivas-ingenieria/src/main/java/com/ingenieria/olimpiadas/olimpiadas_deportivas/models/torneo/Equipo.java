package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Grupo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.ProgramaAcademico;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_equipo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Equipo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length=255)
    private String nombre;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_programa_academico_1", nullable=false)
    private ProgramaAcademico programaAcademico1;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_programa_academico_2")
    private ProgramaAcademico programaAcademico2;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_usuario_capitan")
    private Usuario capitan;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_torneo", nullable=false)
    private Torneo torneo;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_grupo", nullable=false)
    private Grupo grupo;
}
