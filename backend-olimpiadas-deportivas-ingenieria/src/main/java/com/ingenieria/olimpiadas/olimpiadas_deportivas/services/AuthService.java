package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.config.AppProperties;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.auth.AuthDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions.BadRequestException;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Rol;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.usuario.PermisosPorRolRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.usuario.RolRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.usuario.UsuarioRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.security.JwtTokenProvider;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.util.Constants;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PermisosPorRolRepository permisosPorRolRepository;
    private final RolRepository rolRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;

    public AuthService(UsuarioRepository usuarioRepository,
                       RolRepository rolRepository,
                       PermisosPorRolRepository permisosPorRolRepository,
                       JwtTokenProvider jwtTokenProvider,
                       AppProperties appProperties) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.permisosPorRolRepository = permisosPorRolRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.appProperties = appProperties;
    }

    @Transactional
    public AuthDTO authenticateWithGoogle(String token) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new GsonFactory()
            ).setAudience(Collections.singletonList(appProperties.getGoogle().getClientId()))
             .build();

            GoogleIdToken idToken = verifier.verify(token);
            if (idToken == null) throw new BadRequestException("Token de Google inválido o expirado");

            GoogleIdToken.Payload payload = idToken.getPayload();
            String correo = payload.getEmail();
            String nombre = (String) payload.get("name");
            String foto   = (String) payload.get("picture");

            if (correo == null || !correo.toLowerCase().endsWith("@udea.edu.co")) {
                throw new BadRequestException("Solo dominios @udea.edu.co");
            }

            Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(correo).orElseGet(() -> {
                Usuario u = new Usuario();
                u.setCorreo(correo);
                u.setNombre(nombre);
                u.setFotoUrl(foto);
                Rol rolJugador = rolRepository.findByNombreIgnoreCase("JUGADOR")
                        .orElseGet(() -> rolRepository.findById(1).orElse(null));
                u.setRol(rolJugador);
                u.setHabilitado(true);
                return usuarioRepository.save(u);
            });

            if (nombre != null && !nombre.equals(usuario.getNombre())) usuario.setNombre(nombre);
            if (foto != null && !foto.equals(usuario.getFotoUrl())) usuario.setFotoUrl(foto);

            boolean completo = usuario.getDocumento() != null && !usuario.getDocumento().isBlank()
                    && usuario.getEntidadPromotoraSalud() != null && usuario.getProgramaAcademico() != null
                    && usuario.getGenero() != null;
                    
            String roleAuthority = Constants.asSpringRole(
                usuario.getRol() != null ? usuario.getRol().getNombre() : Constants.ROL_JUGADOR
            );
                    
            List<String> permisos = usuario.getRol() == null ? List.of() :
                    permisosPorRolRepository.findByRolId(usuario.getRol().getId())
                            .stream()
                            .map(pp -> pp.getPermiso().getNombre())
                            .toList();
                    
            Collection<String> authorities = Stream.concat(Stream.of(roleAuthority), permisos.stream()).toList();
                    
            String jwt = jwtTokenProvider.generateToken(usuario.getCorreo(), authorities);

            if (Boolean.FALSE.equals(usuario.getHabilitado())) {
                throw new BadRequestException("Usuario deshabilitado. Contacta al administrador.");
            }

            return new AuthDTO(jwt, usuario.getId(), usuario.getNombre(), usuario.getCorreo(), completo, usuario.getFotoUrl());

        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error autenticando con Google: " + e.getMessage());
        }
    }
}
