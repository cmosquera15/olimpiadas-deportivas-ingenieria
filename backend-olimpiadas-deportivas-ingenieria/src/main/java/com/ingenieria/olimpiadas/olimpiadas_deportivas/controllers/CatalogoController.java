package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.catalogo.*;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.evento.TipoEventoDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.CatalogoService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CatalogoController {

    private final CatalogoService svc;
    public CatalogoController(CatalogoService svc) { this.svc = svc; }

    @GetMapping("/programas")
    public ResponseEntity<List<ProgramaAcademicoDTO>> programas() { return ResponseEntity.ok(svc.programas()); }

    @GetMapping("/eps")
    public ResponseEntity<List<EntidadPromotoraSaludDTO>> eps() { return ResponseEntity.ok(svc.eps()); }

    @GetMapping("/generos")
    public ResponseEntity<List<GeneroDTO>> generos() { return ResponseEntity.ok(svc.generos()); }

    @GetMapping("/deportes")
    public ResponseEntity<List<DeporteDTO>> deportes() { return ResponseEntity.ok(svc.deportes()); }

    @GetMapping("/lugares")
    public ResponseEntity<List<LugarDTO>> lugares() { return ResponseEntity.ok(svc.lugares()); }

    @GetMapping("/fases")
    public ResponseEntity<List<FaseDTO>> fases() { return ResponseEntity.ok(svc.fases()); }

    @GetMapping("/jornadas")
    public ResponseEntity<List<JornadaDTO>> jornadas(@RequestParam Integer torneoId) {
        return ResponseEntity.ok(svc.jornadasPorTorneo(torneoId));
    }

    @GetMapping("/grupos")
    public ResponseEntity<List<GrupoDTO>> grupos(@RequestParam Integer torneoId) {
        return ResponseEntity.ok(svc.gruposPorTorneo(torneoId));
    }

    @GetMapping("/resultados")
    public ResponseEntity<List<ResultadoDTO>> resultados() { return ResponseEntity.ok(svc.resultados()); }

    @GetMapping("/tipos-vinculo")
    public ResponseEntity<List<TipoVinculoDTO>> tiposVinculo() { return ResponseEntity.ok(svc.tiposVinculo()); }

    @GetMapping("/tipos-evento")
    public ResponseEntity<List<TipoEventoDTO>> tiposEvento(@RequestParam Integer deporteId) {
        return ResponseEntity.ok(svc.tiposEventoPorDeporte(deporteId));
    }
}
