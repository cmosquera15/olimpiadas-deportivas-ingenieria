package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    @Query("select u from Usuario u where lower(u.correo) = lower(:correo)")
    Optional<Usuario> findByCorreoIgnoreCase(String correo);

    boolean existsByDocumento(String documento);
}
