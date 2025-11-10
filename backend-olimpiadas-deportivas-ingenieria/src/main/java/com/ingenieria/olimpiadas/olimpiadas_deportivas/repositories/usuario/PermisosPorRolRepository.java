package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.PermisosPorRol;

public interface PermisosPorRolRepository extends JpaRepository<PermisosPorRol, Integer> {
    
    @Query("select p.permiso.nombre from PermisosPorRol p where p.rol.id = :rolId")
    List<String> findPermisoNombresByRolId(Integer rolId);

    boolean existsByRolIdAndPermisoId(Integer id, Integer id2);

    List<PermisosPorRol> findByRolId(Integer rolId);
}
