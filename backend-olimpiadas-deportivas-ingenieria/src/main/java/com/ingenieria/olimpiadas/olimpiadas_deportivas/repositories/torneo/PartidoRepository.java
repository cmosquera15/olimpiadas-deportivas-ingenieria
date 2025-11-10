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
}
