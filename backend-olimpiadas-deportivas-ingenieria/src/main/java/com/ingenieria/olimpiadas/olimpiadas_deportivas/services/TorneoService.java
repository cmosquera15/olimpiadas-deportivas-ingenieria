package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.config.AppProperties;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.common.IdNombreDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo.TorneoCreateRequest;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo.TorneoDetailDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo.TorneoListDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions.BadRequestException;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions.NotFoundException;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.mappers.TorneoMapper;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Deporte;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Olimpiada;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo.DeporteRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo.OlimpiadaRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo.TorneoRepository;

@Service
public class TorneoService {

    private final TorneoRepository torneoRepository;
    private final DeporteRepository deporteRepository;
    private final OlimpiadaRepository olimpiadaRepository;
    private final TorneoMapper torneoMapper;
    private final AppProperties appProperties;

    public TorneoService(TorneoRepository torneoRepository,
                         DeporteRepository deporteRepository,
                         OlimpiadaRepository olimpiadaRepository,
                         TorneoMapper torneoMapper,
                         AppProperties appProperties) {
        this.torneoRepository = torneoRepository;
        this.deporteRepository = deporteRepository;
        this.olimpiadaRepository = olimpiadaRepository;
        this.torneoMapper = torneoMapper;
        this.appProperties = appProperties;
    }

    public Page<TorneoListDTO> listar(Integer olimpiadaId, Integer deporteId, Pageable pageable) {
        List<Torneo> base;

        if (olimpiadaId != null && deporteId != null) {
            base = torneoRepository.findByOlimpiadaIdAndDeporteIdOrderByNombreAsc(olimpiadaId, deporteId);
        } else if (olimpiadaId != null) {
            base = torneoRepository.findByOlimpiadaIdOrderByNombreAsc(olimpiadaId);
        } else if (deporteId != null) {
            base = torneoRepository.findByDeporteIdOrderByNombreAsc(deporteId);
        } else {
            base = torneoRepository.findAllOrderByNombreAsc();
        }

        List<TorneoListDTO> dtos = base.stream()
                .map(torneoMapper::toListDTO)
                .toList();

        int start = (int) pageable.getOffset();
        int end   = Math.min(start + pageable.getPageSize(), dtos.size());
        List<TorneoListDTO> slice = start > end ? List.of() : dtos.subList(start, end);

        return new PageImpl<>(slice, pageable, dtos.size());
    }

    public TorneoDetailDTO detalle(Integer id) {
        Torneo t = torneoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));
        String url = appProperties.getReglamentoUrl();
        return torneoMapper.toDetailDTO(t, url);
    }

    public TorneoDetailDTO crear(TorneoCreateRequest req) {
        // Check for duplicate
        torneoRepository.findByNombreAndDeporteIdAndOlimpiadaId(req.nombre(), req.idDeporte(), req.idOlimpiada())
                .ifPresent(t -> {
                    throw new BadRequestException("Ya existe un torneo con ese nombre para el mismo deporte y olimpiada");
                });

        Deporte deporte = deporteRepository.findById(req.idDeporte())
                .orElseThrow(() -> new NotFoundException("Deporte no encontrado"));
        Olimpiada olimpiada = olimpiadaRepository.findById(req.idOlimpiada())
                .orElseThrow(() -> new NotFoundException("Olimpiada no encontrada"));

        Torneo t = Torneo.builder()
                .nombre(req.nombre())
                .deporte(deporte)
                .olimpiada(olimpiada)
                .build();
        t = torneoRepository.save(t);
        String url = appProperties.getReglamentoUrl();
        return torneoMapper.toDetailDTO(t, url);
    }

    public List<IdNombreDTO> listarOpciones(Integer olimpiadaId, Integer deporteId) {
        List<Torneo> base;

        if (olimpiadaId != null && deporteId != null) {
            base = torneoRepository.findByOlimpiadaIdAndDeporteIdOrderByNombreAsc(olimpiadaId, deporteId);
        } else if (olimpiadaId != null) {
            base = torneoRepository.findByOlimpiadaIdOrderByNombreAsc(olimpiadaId);
        } else if (deporteId != null) {
            base = torneoRepository.findByDeporteIdOrderByNombreAsc(deporteId);
        } else {
            base = torneoRepository.findAllOrderByNombreAsc();
        }

        return base.stream()
                .map(torneoMapper::toIdNombre)
                .toList();
    }
}
