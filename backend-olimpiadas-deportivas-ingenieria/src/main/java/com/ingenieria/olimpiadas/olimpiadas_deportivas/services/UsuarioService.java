package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario completarDatos(String correo, Usuario datos) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setDocumento(datos.getDocumento());
        usuario.setProgramaAcademico(datos.getProgramaAcademico());
        usuario.setEntidadPromotoraSalud(datos.getEntidadPromotoraSalud());
        usuario.setRol(datos.getRol());

        return usuarioRepository.save(usuario);
    }
}
