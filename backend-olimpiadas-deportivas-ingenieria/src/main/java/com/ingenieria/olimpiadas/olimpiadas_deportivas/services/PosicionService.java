package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.PosicionDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.EquipoPartido;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.EquipoPartidoRepository;

@Service
public class PosicionService {

    private final EquipoPartidoRepository equipoPartidoRepository;

    public PosicionService(EquipoPartidoRepository equipoPartidoRepository) {
        this.equipoPartidoRepository = equipoPartidoRepository;
    }

    public List<PosicionDTO> obtenerPosiciones() {
        return equipoPartidoRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        ep -> ep.getEquipo().getNombre(),
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    String nombreEquipo = entry.getKey();
                    List<EquipoPartido> partidos = entry.getValue();

                    int jugados = partidos.size();
                    int ganados = (int) partidos.stream().filter(ep -> "Ganado".equalsIgnoreCase(ep.getResultado().getNombre())).count();
                    int perdidos = (int) partidos.stream().filter(ep -> "Perdido".equalsIgnoreCase(ep.getResultado().getNombre())).count();
                    int empatados = (int) partidos.stream().filter(ep -> "Empatado".equalsIgnoreCase(ep.getResultado().getNombre())).count();
                    int puntos = partidos.stream().mapToInt(EquipoPartido::getPuntos).sum();

                    return new PosicionDTO(nombreEquipo, jugados, ganados, perdidos, empatados, puntos);
                })
                .toList();
    }
}
