package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Resultado;

public interface ResultadoRepository extends JpaRepository<Resultado, Integer> {
    Optional<Resultado> findByNombreIgnoreCase(String nombre);
}
