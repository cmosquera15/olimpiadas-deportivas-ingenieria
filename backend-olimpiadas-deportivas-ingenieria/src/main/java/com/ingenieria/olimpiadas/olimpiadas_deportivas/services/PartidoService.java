package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import org.springframework.stereotype.Service;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.PartidoDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.ResultadoRequest;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Resultado;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Partido;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.PartidoRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.ResultadoRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class PartidoService {
    private final PartidoRepository partidoRepository;
    private final ResultadoRepository resultadoRepository;

    public PartidoService(PartidoRepository partidoRepository, ResultadoRepository resultadoRepository) {
        this.partidoRepository = partidoRepository;
        this.resultadoRepository = resultadoRepository;
    }

    public List<Partido> obtenerTodosLosPartidos() {
        return partidoRepository.findAll();
    }

    public Partido guardarPartido(Partido partido) {
        return partidoRepository.save(partido);
    }

    public Partido actualizarPartido(Integer id, Partido partido) {
        Partido partidoExistente = partidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + id));

        partidoExistente.setFecha(partido.getFecha());
        partidoExistente.setLugar(partido.getLugar());
        partidoExistente.setEquipos(partido.getEquipos());

        return partidoRepository.save(partidoExistente);
    }

    @Transactional
    public PartidoDTO actualizarResultado(Integer id, ResultadoRequest request) {
        Partido partido = partidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + id));

        request.resultados().forEach(r -> {
            partido.getEquipos().stream()
                    .filter(ep -> ep.getEquipo().getNombre().equals(r.nombreEquipo()))
                    .findFirst()
                    .ifPresent(ep -> {
                        ep.setPuntos(r.puntos());

                        Resultado resultado = resultadoRepository.findByNombre(r.resultado())
                                .orElseThrow(() -> new RuntimeException("Resultado no encontrado: " + r.resultado()));
                        ep.setResultado(resultado);
                    });
        });

        return new PartidoDTO(partidoRepository.save(partido));
    }

    public List<PartidoDTO> obtenerCalendarioPartidos() {
    return partidoRepository.findAll()
            .stream()
            .map(PartidoDTO::new)
            .toList();
    }
}
