package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.auth.AuthDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.auth.CompletarPerfilRequest;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions.BadRequestException;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions.NotFoundException;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.EntidadPromotoraSalud;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Genero;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.ProgramaAcademico;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.TipoVinculo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo.EntidadPromotoraSaludRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo.GeneroRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo.ProgramaAcademicoRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo.TipoVinculoRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.usuario.UsuarioRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.security.JwtTokenProvider;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EntidadPromotoraSaludRepository entidadPromotoraSaludRepository;
    private final ProgramaAcademicoRepository programaRepository;
    private final GeneroRepository generoRepository;
    private final TipoVinculoRepository tipoVinculoRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          EntidadPromotoraSaludRepository entidadPromotoraSaludRepository,
                          ProgramaAcademicoRepository programaRepository,
                          GeneroRepository generoRepository,
                          TipoVinculoRepository tipoVinculoRepository,
                          JwtTokenProvider jwtTokenProvider) {
        this.usuarioRepository = usuarioRepository;
        this.entidadPromotoraSaludRepository = entidadPromotoraSaludRepository;
        this.programaRepository = programaRepository;
        this.generoRepository = generoRepository;
        this.tipoVinculoRepository = tipoVinculoRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public boolean estaCompleto(String correo) {
        Usuario u = usuarioRepository.findByCorreoIgnoreCase(correo)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return u.getDocumento() != null && !u.getDocumento().isBlank()
                && u.getEntidadPromotoraSalud() != null && u.getProgramaAcademico() != null
                && u.getGenero() != null;
    }

    @Transactional
    public void completarPerfilDesdeJWT(CompletarPerfilRequest req, String bearer) {
        if (bearer == null || !bearer.startsWith("Bearer ")) {
            throw new BadRequestException("Authorization inválido");
        }
        String token = bearer.substring("Bearer ".length()).trim();
        String correo = jwtTokenProvider.getEmailFromToken(token);

        if (estaCompleto(correo)) {
            throw new BadRequestException("Perfil ya completado. Para cambios, acércate a Bienestar/Admin.");
        }
        completarPerfil(correo, req);
    }

    @Transactional
    public Usuario completarPerfil(String correo, CompletarPerfilRequest req) {
        Usuario u = usuarioRepository.findByCorreoIgnoreCase(correo)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (req.getDocumento() == null || req.getDocumento().isBlank()) {
            throw new BadRequestException("Documento obligatorio");
        }

        if (u.getDocumento() != null && !u.getDocumento().equals(req.getDocumento())) {
            throw new BadRequestException("No se puede cambiar el documento una vez establecido");
        }

        EntidadPromotoraSalud eps = entidadPromotoraSaludRepository.findById(req.getId_eps())
                .orElseThrow(() -> new NotFoundException("EPS no encontrada"));
        ProgramaAcademico prog = programaRepository.findById(req.getId_programa_academico())
                .orElseThrow(() -> new NotFoundException("Programa no encontrado"));
        Genero genero = generoRepository.findById(req.getId_genero())
                .orElseThrow(() -> new NotFoundException("Género no encontrado"));
        TipoVinculo vinculo = tipoVinculoRepository.findById(req.getId_tipo_vinculo())
                .orElseThrow(() -> new NotFoundException("Tipo vínculo no encontrado"));

        u.setDocumento(req.getDocumento());
        u.setEntidadPromotoraSalud(eps);
        u.setProgramaAcademico(prog);
        u.setGenero(genero);
        u.setTipoVinculo(vinculo);

        return usuarioRepository.save(u);
    }

    public AuthDTO me(String bearer) {
        if (bearer == null || !bearer.startsWith("Bearer ")) {
            throw new BadRequestException("Authorization inválido");
        }
        String token = bearer.substring("Bearer ".length()).trim();
        String correo = jwtTokenProvider.getEmailFromToken(token);

        Usuario u = usuarioRepository.findByCorreoIgnoreCase(correo)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        boolean completo = estaCompleto(correo);
        return new AuthDTO(null, u.getNombre(), u.getCorreo(), completo, u.getFotoUrl());
    }

    public Usuario getById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }
}
