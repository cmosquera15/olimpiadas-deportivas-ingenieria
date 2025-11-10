package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo;

public interface TorneoRepository extends JpaRepository<Torneo, Integer> {
    List<Torneo> findByActivoTrueOrderByAnioDescNombreAsc();

    List<Torneo> findByDeporteIdOrderByAnioDescNombreAsc(Integer deporteId);

    List<Torneo> findByDeporteIdAndActivoTrueOrderByAnioDescNombreAsc(Integer deporteId);
}
