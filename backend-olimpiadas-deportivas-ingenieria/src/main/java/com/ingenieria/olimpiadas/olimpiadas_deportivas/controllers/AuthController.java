package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.auth.AuthDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.auth.CompletarPerfilRequest;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.AuthService;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    public AuthController(AuthService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/google-login")
    public ResponseEntity<AuthDTO> loginGoogle(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        return ResponseEntity.ok(authService.authenticateWithGoogle(token));
    }

    @PostMapping("/completar-perfil")
    public ResponseEntity<Void> completarPerfil(@Valid @RequestBody CompletarPerfilRequest req,
                                                @RequestHeader("Authorization") String bearer) {
        usuarioService.completarPerfilDesdeJWT(req, bearer);
        return ResponseEntity.ok().build();
    }
}
