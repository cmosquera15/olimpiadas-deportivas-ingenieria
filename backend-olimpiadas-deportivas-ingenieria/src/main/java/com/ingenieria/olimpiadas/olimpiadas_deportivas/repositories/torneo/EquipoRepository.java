package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

    List<Equipo> findByTorneoIdOrderByNombreAsc(Integer torneoId);

    @Query("""
           select count(e) 
           from Equipo e 
           where e.torneo.id = :torneoId 
           and e.torneo.deporte.id = :deporteId
           """)
    long countByTorneoAndDeporte(Integer torneoId, Integer deporteId);

    @Query("""
           select e from Equipo e 
           where e.grupo.id = :grupoId
           order by e.nombre asc
           """)
    List<Equipo> findByGrupo(Integer grupoId);
}
