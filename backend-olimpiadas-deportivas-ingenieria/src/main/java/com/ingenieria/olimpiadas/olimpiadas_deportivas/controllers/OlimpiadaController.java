package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.common.IdNombreDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.olimpiada.OlimpiadaCreateDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.olimpiada.OlimpiadaDetailDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.olimpiada.OlimpiadaUpdateDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Olimpiada;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.OlimpiadaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/olimpiadas")
public class OlimpiadaController {

    private final OlimpiadaService svc;

    public OlimpiadaController(OlimpiadaService svc) {
        this.svc = svc;
    }

    @GetMapping
    public ResponseEntity<List<IdNombreDTO>> listarActivas() {
        return ResponseEntity.ok(svc.listarActivas());
    }

    @GetMapping("/todas")
    public ResponseEntity<List<OlimpiadaDetailDTO>> listarTodas() {
        return ResponseEntity.ok(svc.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OlimpiadaDetailDTO> obtenerPorId(@PathVariable Integer id) {
        Olimpiada olimpiada = svc.obtenerPorId(id);
        List<com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo> torneos = 
            svc.getTorneosByOlimpiadaId(id);
        
        List<com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.olimpiada.TorneoSummaryDTO> torneoSummaries = 
            torneos.stream()
                .map(t -> new com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.olimpiada.TorneoSummaryDTO(
                    t.getId(),
                    t.getNombre(),
                    t.getDeporte().getId(),
                    t.getDeporte().getNombre()
                ))
                .toList();
        
        return ResponseEntity.ok(new OlimpiadaDetailDTO(
            olimpiada.getId(),
            olimpiada.getNombre(),
            olimpiada.getSlug(),
            olimpiada.getEdicion(),
            olimpiada.getAnio(),
            olimpiada.getActivo(),
            torneoSummaries
        ));
    }

    @PostMapping
    public ResponseEntity<OlimpiadaDetailDTO> crear(@Valid @RequestBody OlimpiadaCreateDTO req) {
        return ResponseEntity.ok(svc.crear(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OlimpiadaDetailDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody OlimpiadaUpdateDTO req) {
        return ResponseEntity.ok(svc.actualizar(id, req));
    }
}
