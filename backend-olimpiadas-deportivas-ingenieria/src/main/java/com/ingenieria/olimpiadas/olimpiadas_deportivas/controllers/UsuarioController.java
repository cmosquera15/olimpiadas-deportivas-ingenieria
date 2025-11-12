package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.auth.AuthDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.usuario.UsuarioUpdateDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public ResponseEntity<AuthDTO> me(@RequestHeader("Authorization") String bearer) {
        return ResponseEntity.ok(usuarioService.me(bearer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> byId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.getById(id));
    }

    @PatchMapping("/me")
    public ResponseEntity<Usuario> actualizarPerfilPropio(@RequestHeader("Authorization") String bearer,
                                                          @RequestBody UsuarioUpdateDTO req) {
        return ResponseEntity.ok(usuarioService.editarPerfilPropio(bearer, req));
    }

    // Admin endpoints moved to AdminUsuarioController to expose /api/admin/usuarios
}
