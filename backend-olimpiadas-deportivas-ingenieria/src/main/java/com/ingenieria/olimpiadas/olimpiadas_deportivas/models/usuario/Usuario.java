package com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.EntidadPromotoraSalud;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Genero;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.ProgramaAcademico;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.TipoVinculo;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(schema="olimpiadas_ingenieria", name="tbl_usuario")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, length=255)
    private String nombre;

    @Column(unique=true, length=255) // puede ser NULL hasta completar perfil
    private String documento;

    @Column(nullable=false, unique=true, length=255)
    private String correo;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_programa_academico")
    private ProgramaAcademico programaAcademico;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_eps")
    private EntidadPromotoraSalud entidadPromotoraSalud;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_rol", nullable=false)
    private Rol rol;

    @Column(name="foto_url")
    private String fotoUrl;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_tipo_vinculo")
    private TipoVinculo tipoVinculo;

    @Builder.Default
    @Column(nullable=false)
    private Boolean habilitado = Boolean.TRUE;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="id_genero")
    private Genero genero;
}
