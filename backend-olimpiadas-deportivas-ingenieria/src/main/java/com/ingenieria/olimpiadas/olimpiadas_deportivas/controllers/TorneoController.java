package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo.TorneoDetailDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo.TorneoListDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.GeneradorLlavesService;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.TorneoService;

@RestController
@RequestMapping("/api/torneos")
public class TorneoController {

    private final TorneoService svc;
    private final GeneradorLlavesService generadorLlavesService;

    public TorneoController(TorneoService svc, GeneradorLlavesService generadorLlavesService) {
        this.svc = svc;
        this.generadorLlavesService = generadorLlavesService;
    }

    @GetMapping
    public ResponseEntity<Page<TorneoListDTO>> listar(
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) Integer deporteId,
            @RequestParam(required = false) Integer anio,
            Pageable pageable) {
        return ResponseEntity.ok(svc.listar(activo, deporteId, anio, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TorneoDetailDTO> detalle(@PathVariable Integer id) {
        return ResponseEntity.ok(svc.detalle(id));
    }

    @PostMapping("/{id}/generar-llaves")
    @PreAuthorize("hasAuthority('Partidos_Crear')")
    public ResponseEntity<Void> generarLlaves(@PathVariable Integer id) {
        generadorLlavesService.generarLlaves(id);
        return ResponseEntity.noContent().build();
    }
}
