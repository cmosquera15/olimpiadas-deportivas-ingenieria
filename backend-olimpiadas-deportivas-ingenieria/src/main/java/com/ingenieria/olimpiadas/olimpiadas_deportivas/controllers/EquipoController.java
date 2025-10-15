package com.ingenieria.olimpiadas.olimpiadas_deportivas.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.EquipoDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Equipo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.services.EquipoService;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {
    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public List<EquipoDTO> obtenerTodosLosEquipos() {
        return equipoService.obtenerTodosLosEquipos();
    }

    @PostMapping
    public EquipoDTO crearEquipo(@RequestBody Equipo equipo) {
        return new EquipoDTO(equipoService.guardarEquipo(equipo));
    }

    @PutMapping("/{id}")
    public Equipo actualizarEquipo(@PathVariable Integer id, @RequestBody Equipo equipo) {
        return equipoService.actualizarEquipo(id, equipo);
    }

    @DeleteMapping("/{id}")
    public void eliminarEquipo(@PathVariable Integer id) {
        equipoService.eliminarEquipo(id);
    }

    @GetMapping("/{id}")
    public EquipoDTO obtenerEquipoPorId(@PathVariable Integer id) {
        return equipoService.obtenerEquipoPorId(id);
    }
}
