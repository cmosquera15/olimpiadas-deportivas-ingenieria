package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Fase;

public interface FaseRepository extends JpaRepository<Fase, Integer> {
    Optional<Fase> findByNombreIgnoreCase(String nombre);

    Optional<Fase> findByNombreAndTorneoId(String nombre, Integer torneoId);
}
