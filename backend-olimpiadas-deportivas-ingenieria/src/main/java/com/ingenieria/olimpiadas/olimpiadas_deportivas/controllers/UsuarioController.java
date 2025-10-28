package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.UsuarioDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/completar")
    public ResponseEntity<UsuarioDTO> completarRegistro(@RequestBody Usuario datos, Principal principal) {
        String correo = principal.getName();
        Usuario actualizado = usuarioService.completarDatos(correo, datos);

        UsuarioDTO response = new UsuarioDTO(
                actualizado.getNombre(),
                actualizado.getCorreo(),
                actualizado.getDocumento(),
                actualizado.getProgramaAcademico(),
                actualizado.getEntidadPromotoraSalud(),
                actualizado.getRol()
        );

        return ResponseEntity.ok(response);
    }
}
