package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.TorneoRepository;

@Service
public class TorneoService {
    private final TorneoRepository torneoRepository;

    public TorneoService(TorneoRepository torneoRepository) {
        this.torneoRepository = torneoRepository;
    }

    public List<Torneo> obtenerTodosLosTorneos() {
        return torneoRepository.findAll();
    }

    public Torneo guardarTorneo(Torneo torneo) {
        return torneoRepository.save(torneo);
    }

    public Torneo actualizarTorneo(Integer id, Torneo torneo) {
        return torneoRepository.findById(id)
                .map(existingTorneo -> {
                    existingTorneo.setNombre(torneo.getNombre());
                    existingTorneo.setDeporte(torneo.getDeporte());
                    existingTorneo.setActivo(torneo.getActivo());
                    return torneoRepository.save(existingTorneo);
                })
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado con id: " + id));
    }

    public void eliminarTorneo(Integer id) {
        torneoRepository.deleteById(id);
    }

    public List<String> obtenerNombresTorneos(String deporte, String activo) {
        if (deporte != null) {
            return torneoRepository.findNombresByDeporte(deporte);
        } else if (activo != null) {
            boolean isActivo = Boolean.parseBoolean(activo);
            return torneoRepository.findNombresByActivo(isActivo);
        } else {
            return torneoRepository.findAllNombres();
        }
    }
}
