package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Jornada;

public interface JornadaRepository extends JpaRepository<Jornada, Integer> {
    
    List<Jornada> findByTorneoIdOrderByNumeroAsc(Integer torneoId);

    Optional<Jornada> findByTorneoIdAndNumero(Integer torneoId, Integer numero);
}
