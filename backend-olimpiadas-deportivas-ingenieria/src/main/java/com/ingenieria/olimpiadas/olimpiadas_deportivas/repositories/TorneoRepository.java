package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo;

public interface TorneoRepository extends JpaRepository<Torneo, Integer> {
    @Query("SELECT t.nombre FROM Torneo t WHERE t.deporte.nombre = :nombreDeporte")
    List<String> findNombresByDeporte(@Param("nombreDeporte") String nombreDeporte);
    
    List<String> findNombresByActivo(boolean activo);

    @Query("SELECT t.nombre FROM Torneo t")
    List<String> findAllNombres();
}
