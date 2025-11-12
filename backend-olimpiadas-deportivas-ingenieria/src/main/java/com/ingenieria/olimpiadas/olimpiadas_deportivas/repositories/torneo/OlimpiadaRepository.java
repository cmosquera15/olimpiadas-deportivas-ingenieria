package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Olimpiada;

public interface OlimpiadaRepository extends JpaRepository<Olimpiada, Integer> {
  Optional<Olimpiada> findBySlug(String slug);

  List<Olimpiada> findByActivoTrueOrderByAnioDescNombreAsc();
  
  List<Olimpiada> findAllByOrderByAnioDescNombreAsc();
}
