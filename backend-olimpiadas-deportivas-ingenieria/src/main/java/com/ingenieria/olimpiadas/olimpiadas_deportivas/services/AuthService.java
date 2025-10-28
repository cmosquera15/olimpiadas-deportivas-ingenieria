package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Value;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.AuthDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.UsuarioRepository;

@Service
public class AuthService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    private final UsuarioRepository usuarioRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UsuarioRepository usuarioRepository, JwtTokenProvider jwtTokenProvider) {
        this.usuarioRepository = usuarioRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthDTO authenticateWithGoogle(String token) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    new GsonFactory()
            ).setAudience(Collections.singletonList(googleClientId)).build();

            GoogleIdToken idToken = verifier.verify(token);
            if (idToken == null) {
                throw new IllegalArgumentException("Token inválido o expirado");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            String correo = payload.getEmail();
            String nombre = (String) payload.get("name");

            Usuario usuario = usuarioRepository.findByCorreo(correo)
                    .orElseGet(() -> {
                        Usuario nuevo = new Usuario();
                        nuevo.setCorreo(correo);
                        nuevo.setNombre(nombre);
                        return usuarioRepository.save(nuevo);
                    });

            String jwt = jwtTokenProvider.generateToken(usuario.getCorreo());

            return new AuthDTO(jwt, usuario.getNombre(), usuario.getCorreo());

        } catch (Exception e) {
            throw new RuntimeException("Error al autenticar con Google: " + e.getMessage(), e);
        }
    }
}
