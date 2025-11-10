package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.EquiposPorPartido;

public interface EquiposPorPartidoRepository extends JpaRepository<EquiposPorPartido, Integer> {

    List<EquiposPorPartido> findByPartidoId(Integer partidoId);

    @Query("""
           select epp from EquiposPorPartido epp
           where epp.partido.id = :partidoId and epp.equipo.id = :equipoId
           """)
    List<EquiposPorPartido> findByPartidoAndEquipo(Integer partidoId, Integer equipoId);

    @Query("""
           select count(epp) from EquiposPorPartido epp
           where epp.partido.id = :partidoId
           """)
    long countByPartido(Integer partidoId);
}
