package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.ReglasPorDeporte;

public interface ReglasPorDeporteRepository extends JpaRepository<ReglasPorDeporte, Integer> {
    Optional<ReglasPorDeporte> findByDeporteId(Integer deporteId);
}
