package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.EquipoDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Equipo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.EquipoRepository;

@Service
public class EquipoService {
    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<EquipoDTO> obtenerTodosLosEquipos() {
        return equipoRepository.findAll()
                .stream()
                .map(EquipoDTO::new)
                .toList();
    }

    public Equipo guardarEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public void eliminarEquipo(Integer id) {
        equipoRepository.deleteById(id);
    }

    public Equipo actualizarEquipo(Integer id, Equipo equipo) {
        Equipo equipoExistente = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        equipoExistente.setNombre(equipo.getNombre());
        equipoExistente.setDeporte(equipo.getDeporte());
        return equipoRepository.save(equipoExistente);
    }

    public EquipoDTO obtenerEquipoPorId(Integer id) {
        return equipoRepository.findById(id)
                .map(EquipoDTO::new)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }
}

