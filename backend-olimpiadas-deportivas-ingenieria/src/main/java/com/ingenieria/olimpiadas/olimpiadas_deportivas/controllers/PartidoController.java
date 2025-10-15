package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import org.springframework.web.bind.annotation.*;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.PartidoDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.ResultadoRequest;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Partido;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.PartidoService;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {
    private final PartidoService partidoService;

    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @GetMapping
    public List<Partido> obtenerTodosLosPartidosEndpoint() {
        return partidoService.obtenerTodosLosPartidos();
    }

    @PostMapping
    public Partido crearPartido(@RequestBody Partido partido) {
        return partidoService.guardarPartido(partido);
    }

    @PutMapping("/{id}")
    public Partido actualizarPartido(@PathVariable Integer id, @RequestBody Partido partido) {
        return partidoService.actualizarPartido(id, partido);
    }

    @PutMapping("/{id}/resultado")
    public PartidoDTO actualizarResultado(@PathVariable Integer id, @RequestBody ResultadoRequest request) {
        return partidoService.actualizarResultado(id, request);
    }

    @GetMapping("/calendario")
    public List<PartidoDTO> obtenerCalendarioPartidos() {
        return partidoService.obtenerCalendarioPartidos();
    }
}
