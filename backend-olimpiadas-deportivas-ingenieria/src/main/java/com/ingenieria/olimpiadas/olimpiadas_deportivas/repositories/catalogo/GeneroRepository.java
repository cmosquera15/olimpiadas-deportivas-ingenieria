package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Integer> {
}
