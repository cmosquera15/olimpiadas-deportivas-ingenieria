package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
    
    List<Grupo> findByTorneoIdOrderByNombreAsc(Integer torneoId);

    Optional<Grupo> findByTorneoIdAndNombre(Integer torneoId, String nombre);
}
