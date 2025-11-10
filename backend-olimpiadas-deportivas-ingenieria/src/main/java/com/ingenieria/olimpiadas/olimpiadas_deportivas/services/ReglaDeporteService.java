package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import org.springframework.stereotype.Service;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions.BadRequestException;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.ReglasPorDeporte;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Equipo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo.ReglasPorDeporteRepository;

@Service
public class ReglaDeporteService {

    private final ReglasPorDeporteRepository reglasRepo;

    public ReglaDeporteService(ReglasPorDeporteRepository reglasRepo) {
        this.reglasRepo = reglasRepo;
    }

    public int puntosVictoria(String nombreDeporteUpper) {
        return nombreDeporteUpper.contains("Baloncesto") ? 2 : 3;
    }
    public int puntosEmpate(String nombreDeporteUpper) {
        return nombreDeporteUpper.contains("Baloncesto") ? 0 : 1;
    }
    public int puntosDerrota() { return 0; }

    public int minJugadores(Equipo equipo) {
        Integer deporteId = equipo.getTorneo() != null && equipo.getTorneo().getDeporte() != null
                ? equipo.getTorneo().getDeporte().getId() : null;
        if (deporteId == null) return 0;
        return reglasRepo.findByDeporteId(deporteId)
                .map(ReglasPorDeporte::getMinJugadoresEquipo)
                .orElse(0);
    }

    public boolean requiereMujer(Equipo equipo) {
        Integer deporteId = equipo.getTorneo() != null && equipo.getTorneo().getDeporte() != null
                ? equipo.getTorneo().getDeporte().getId() : null;
        if (deporteId == null) return false;
        return reglasRepo.findByDeporteId(deporteId)
                .map(ReglasPorDeporte::getRequiereMujerEnPlantilla)
                .orElse(false);
    }

    public void validarPlantillaMinimos(Equipo equipo,
                                        long totalJugadores,
                                        long totalMujeres) {
        int min = minJugadores(equipo);
        if (totalJugadores < min) {
            throw new BadRequestException("El equipo no cumple el mínimo de jugadores: " + min);
        }
        if (requiereMujer(equipo) && totalMujeres == 0) {
            throw new BadRequestException("Se requiere al menos una mujer en la plantilla.");
        }
    }
}
