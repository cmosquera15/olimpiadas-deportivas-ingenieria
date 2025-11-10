package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo.TablaPosicionesDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.TablaPosicionesService;

@RestController
@RequestMapping("/api/posiciones")
public class PosicionesController {

    private final TablaPosicionesService svc;
    public PosicionesController(TablaPosicionesService svc) { this.svc = svc; }

    @GetMapping
    public ResponseEntity<TablaPosicionesDTO> tabla(
            @RequestParam Integer torneoId,
            @RequestParam(required = false) Integer grupoId) {
        return ResponseEntity.ok(svc.calcular(torneoId, grupoId));
    }
}
