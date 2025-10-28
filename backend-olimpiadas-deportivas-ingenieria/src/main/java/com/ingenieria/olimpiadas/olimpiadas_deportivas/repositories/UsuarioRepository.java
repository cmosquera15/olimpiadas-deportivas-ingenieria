package com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
}
