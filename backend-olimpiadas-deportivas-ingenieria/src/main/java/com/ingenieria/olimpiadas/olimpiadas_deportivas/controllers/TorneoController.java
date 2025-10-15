package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.TorneoService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/torneos")
public class TorneoController {
    private final TorneoService torneoService;

    public TorneoController(TorneoService torneoService) {
        this.torneoService = torneoService;
    }

    @GetMapping
    public List<Torneo> obtenerTodosLosTorneosEndpoint() {
        return torneoService.obtenerTodosLosTorneos();
    }
    
    @PostMapping
    public Torneo crearTorneo(@RequestBody Torneo torneo) {
        return torneoService.guardarTorneo(torneo);
    }

    @PutMapping("/{id}")
    public Torneo actualizarTorneo(@PathVariable Integer id, @RequestBody Torneo torneo) {
        return torneoService.actualizarTorneo(id, torneo);
    }

    @DeleteMapping("/{id}")
    public void eliminarTorneo(@PathVariable Integer id) {
        torneoService.eliminarTorneo(id);
    }

    @GetMapping("/nombres")
    public List<String> obtenerNombresTorneos(@RequestParam(required = false) String deporte, @RequestParam(required = false) String activo) {
        return torneoService.obtenerNombresTorneos(deporte, activo);
    }
}
