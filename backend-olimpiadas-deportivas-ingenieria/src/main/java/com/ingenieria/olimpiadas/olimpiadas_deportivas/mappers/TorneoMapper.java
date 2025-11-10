package com.ingenieria.olimpiadas.olimpiadas_deportivas.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.common.IdNombreDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo.TorneoDetailDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo.TorneoListDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Deporte;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TorneoMapper {

    IdNombreDTO mapDeporteToIdNombre(Deporte d);

    default TorneoListDTO toListDTO(Torneo t) {
        if (t == null) return null;
        Integer deporteId = t.getDeporte() != null ? t.getDeporte().getId() : null;
        return new TorneoListDTO(t.getId(), t.getNombre(), t.getAnio(), deporteId, t.getActivo());
    }

    default TorneoDetailDTO toDetailDTO(Torneo t, String reglamentoUrl) {
        if (t == null) return null;
        Integer deporteId = t.getDeporte() != null ? t.getDeporte().getId() : null;
        return new TorneoDetailDTO(t.getId(), t.getNombre(), t.getAnio(), deporteId, t.getActivo(), reglamentoUrl);
    }

    default IdNombreDTO toIdNombre(Torneo t) {
        return t == null ? null : new IdNombreDTO(t.getId(), t.getNombre());
    }
}
