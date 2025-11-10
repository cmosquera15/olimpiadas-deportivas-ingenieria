package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.evento.EventoCreateDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.evento.EventoDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.EventoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService svc;
    public EventoController(EventoService svc) { this.svc = svc; }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarPorPartido(@RequestParam Integer partidoId) {
        return ResponseEntity.ok(svc.listarPorPartido(partidoId));
    }

    @PostMapping
    public ResponseEntity<EventoDTO> crear(@Valid @RequestBody EventoCreateDTO req) {
        return ResponseEntity.ok(svc.crear(req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        svc.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
