package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.config.AppProperties;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo.TorneoDetailDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo.TorneoListDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions.NotFoundException;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.mappers.TorneoMapper;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo.TorneoRepository;

@Service
public class TorneoService {

    private final TorneoRepository torneoRepository;
    private final TorneoMapper torneoMapper;
    private final AppProperties appProperties;

    public TorneoService(TorneoRepository torneoRepository, TorneoMapper torneoMapper, AppProperties appProperties) {
        this.torneoRepository = torneoRepository;
        this.torneoMapper = torneoMapper;
        this.appProperties = appProperties;
    }

    public Page<TorneoListDTO> listar(Boolean activo, Integer deporteId, Integer anio, Pageable pageable) {
        List<Torneo> base;
        if (deporteId != null && Boolean.TRUE.equals(activo)) {
            base = torneoRepository.findByDeporteIdAndActivoTrueOrderByAnioDescNombreAsc(deporteId);
        } else if (deporteId != null) {
            base = torneoRepository.findByDeporteIdOrderByAnioDescNombreAsc(deporteId);
        } else if (Boolean.TRUE.equals(activo)) {
            base = torneoRepository.findByActivoTrueOrderByAnioDescNombreAsc();
        } else {
            base = torneoRepository.findAll(Sort.by(Sort.Direction.DESC, "anio").and(Sort.by("nombre")));
        }

        if (anio != null) {
            base = base.stream().filter(t -> anio.equals(t.getAnio())).collect(Collectors.toList());
        }

        List<TorneoListDTO> dtos = base.stream().map(torneoMapper::toListDTO).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), dtos.size());
        List<TorneoListDTO> slice = start > end ? List.of() : dtos.subList(start, end);

        return new PageImpl<>(slice, pageable, dtos.size());
    }

    public TorneoDetailDTO detalle(Integer id) {
        Torneo t = torneoRepository.findById(id).orElseThrow(() -> new NotFoundException("Torneo no encontrado"));
        String url = appProperties.getReglamentoUrl();
        return torneoMapper.toDetailDTO(t, url);
    }
}
