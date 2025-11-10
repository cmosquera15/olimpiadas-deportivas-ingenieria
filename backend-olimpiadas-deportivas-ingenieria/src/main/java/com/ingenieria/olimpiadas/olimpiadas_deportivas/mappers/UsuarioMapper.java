package com.ingenieria.olimpiadas.olimpiadas_deportivas.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.common.IdNombreDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Deporte;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    IdNombreDTO mapDeporteToIdNombre(Deporte d);

    default IdNombreDTO toIdNombre(Usuario u) {
        return u == null ? null : new IdNombreDTO(u.getId(), u.getNombre());
    }

    default String fotoUrl(Usuario u) { return u == null ? null : u.getFotoUrl(); }
    default String correo(Usuario u)  { return u == null ? null : u.getCorreo(); }
}
