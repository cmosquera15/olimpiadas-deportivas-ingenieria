package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Lugar;

public interface LugarRepository extends JpaRepository<Lugar, Integer> {
    Optional<Lugar> findByNombreIgnoreCase(String nombre);
}
