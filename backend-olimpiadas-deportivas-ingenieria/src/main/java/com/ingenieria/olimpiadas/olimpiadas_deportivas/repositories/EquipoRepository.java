package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

}
