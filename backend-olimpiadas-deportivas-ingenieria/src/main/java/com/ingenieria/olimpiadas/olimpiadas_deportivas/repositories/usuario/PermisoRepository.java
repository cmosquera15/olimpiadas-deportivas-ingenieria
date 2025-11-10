package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
    Optional<Permiso> findByNombreIgnoreCase(String nombre);
}
