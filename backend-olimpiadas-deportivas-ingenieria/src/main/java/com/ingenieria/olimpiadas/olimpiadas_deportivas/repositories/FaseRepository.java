package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Fase;


public interface FaseRepository extends JpaRepository<Fase, Integer>{
}
