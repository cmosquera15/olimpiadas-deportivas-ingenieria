package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.evento;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.evento.TipoEvento;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Integer> {

    List<TipoEvento> findByDeporteIdOrderByNombreAsc(Integer deporteId);

    Optional<TipoEvento> findByDeporteIdAndNombreIgnoreCase(Integer deporteId, String nombre);
}
