package com.ingenieria.olimpiadas.olimpiadas_deportivas.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.common.IdNombreDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo.EquipoDetailDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo.EquipoListDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo.EquipoListViewDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Deporte;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Equipo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EquipoMapper {

    IdNombreDTO mapDeporteToIdNombre(Deporte d);

    default EquipoListDTO toListDTO(Equipo e) {
        if (e == null) return null;
        Integer torneoId = e.getTorneo() != null ? e.getTorneo().getId() : null;
        String torneoNombre = e.getTorneo() != null ? e.getTorneo().getNombre() : null;
        Integer grupoId = e.getGrupo() != null ? e.getGrupo().getId() : null;
        Integer prog1Id = e.getProgramaAcademico1() != null ? e.getProgramaAcademico1().getId() : null;
        Integer prog2Id = e.getProgramaAcademico2() != null ? e.getProgramaAcademico2().getId() : null;
        Integer capitanId = e.getCapitan() != null ? e.getCapitan().getId() : null;

        return new EquipoListDTO(
            e.getId(),
            e.getNombre(),
            torneoId,
            torneoNombre,
            grupoId,
            prog1Id,
            prog2Id,
            capitanId,
            0 // integrantesCount - TODO: implement actual count
        );
    }

    default EquipoListViewDTO toListView(Equipo e) {
        if (e == null) return null;
        IdNombreDTO torneo = e.getTorneo() != null ? new IdNombreDTO(e.getTorneo().getId(), e.getTorneo().getNombre()) : null;
        IdNombreDTO grupo  = e.getGrupo()  != null ? new IdNombreDTO(e.getGrupo().getId(),  e.getGrupo().getNombre())  : null;
        return new EquipoListViewDTO(e.getId(), e.getNombre(), torneo, grupo);
    }

    default EquipoDetailDTO toDetailDTO(Equipo e) {
        if (e == null) return null;

        Integer torneoId = e.getTorneo() != null ? e.getTorneo().getId() : null;
        String  torneoNm = e.getTorneo() != null ? e.getTorneo().getNombre() : null;

        Integer grupoId = e.getGrupo() != null ? e.getGrupo().getId() : null;
        String  grupoNm = e.getGrupo() != null ? e.getGrupo().getNombre() : null;

        Integer prog1Id = e.getProgramaAcademico1() != null ? e.getProgramaAcademico1().getId() : null;
        String  prog1Nm = e.getProgramaAcademico1() != null ? e.getProgramaAcademico1().getNombre() : null;

        Integer prog2Id = e.getProgramaAcademico2() != null ? e.getProgramaAcademico2().getId() : null;
        String  prog2Nm = e.getProgramaAcademico2() != null ? e.getProgramaAcademico2().getNombre() : null;

        Integer capId = e.getCapitan() != null ? e.getCapitan().getId() : null;
        String  capNm = e.getCapitan() != null ? e.getCapitan().getNombre() : null;

        return new EquipoDetailDTO(
                e.getId(),
                e.getNombre(),
                torneoId, torneoNm,
                grupoId,  grupoNm,
                prog1Id,  prog1Nm,
                prog2Id,  prog2Nm,
                capId,    capNm
        );
    }
}
