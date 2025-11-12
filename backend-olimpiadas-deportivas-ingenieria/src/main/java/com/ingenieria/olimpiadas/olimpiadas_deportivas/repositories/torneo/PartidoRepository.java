package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Partido;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PartidoRepository extends JpaRepository<Partido, Integer> {

    @Query("""
           select p from Partido p
           where p.torneo.id = :torneoId
             and p.fecha between :desde and :hasta
           order by p.fecha asc, p.hora asc
           """)
    List<Partido> findByTorneoAndFechaBetween(Integer torneoId, LocalDate desde, LocalDate hasta);

    @Query("""
           select p from Partido p
           where p.torneo.id = :torneoId
           order by p.fecha asc, p.hora asc
           """)
    List<Partido> findByTorneoOrdered(Integer torneoId);

    // NUEVO: listar por olimpiada (atraviesa torneo)
    @Query("""
           select p from Partido p
           where p.torneo.olimpiada.id = :olimpiadaId
           order by p.fecha asc, p.hora asc
           """)
    List<Partido> findByOlimpiadaOrdered(Integer olimpiadaId);

    // NUEVO: filtro flexible por olimpiada + opcionales
    @Query("""
           select p from Partido p
           where p.torneo.olimpiada.id = :olimpiadaId
             and (:torneoId is null  or p.torneo.id = :torneoId)
             and (:faseId   is null  or p.fase.id   = :faseId)
             and (:grupoId  is null  or p.grupo.id  = :grupoId)
             and (:arbitroId is null or p.arbitro.id = :arbitroId)
           order by p.fecha asc, p.hora asc
           """)
    List<Partido> findFilteredByOlimpiada(Integer olimpiadaId,
                                          Integer torneoId,
                                          Integer faseId,
                                          Integer grupoId,
                                          Integer arbitroId);

    @Query("""
           select p from Partido p
           where p.torneo.id = :torneoId
             and p.fecha = :fecha
             and p.hora = :hora
           """)
    List<Partido> findConflictsSameDateTime(Integer torneoId, LocalDate fecha, LocalTime hora);

    @Query("""
           select p from Partido p
           where p.torneo.id = :torneoId
             and p.fecha = :fecha
             and p.hora = :hora
             and p.lugar.id = :lugarId
           """)
    List<Partido> findConflictsSameDateTimeAndLugar(Integer torneoId, LocalDate fecha, LocalTime hora, Integer lugarId);

    @Query("""
           select p from Partido p
           where p.torneo.id = :torneoId
             and p.fase.nombre = :faseNombre
           """)
    List<Partido> findByTorneoIdAndFaseNombre(Integer torneoId, String faseNombre);
}
