package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.EntidadPromotoraSalud;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.ProgramaAcademico;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Rol;

public record UsuarioDTO(
    String nombre,
    String correo,
    String documento,
    ProgramaAcademico programaAcademico,
    EntidadPromotoraSalud entidadPromotoraSalud,
    Rol rol
) {

}
