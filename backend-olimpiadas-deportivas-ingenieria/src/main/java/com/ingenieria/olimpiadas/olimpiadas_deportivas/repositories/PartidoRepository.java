package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Fase;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Grupo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Jornada;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Partido;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo;

import java.util.List;

public interface PartidoRepository extends JpaRepository<Partido, Integer> {
    List<Partido> findByJornada(Jornada jornada);
    List<Partido> findByFase(Fase fase);
    List<Partido> findByGrupo(Grupo grupo);
    List<Partido> findByTorneo(Torneo torneo);
}
