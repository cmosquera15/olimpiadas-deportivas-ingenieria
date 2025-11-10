package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.catalogo.*;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.evento.TipoEventoDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.mappers.CatalogoMapper;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo.*;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.evento.TipoEventoRepository;

@Service
public class CatalogoService {

    private final DeporteRepository deporteRepository;
    private final EntidadPromotoraSaludRepository entidadPromotoraSaludRepository;
    private final FaseRepository faseRepository;
    private final GeneroRepository generoRepository;
    private final GrupoRepository grupoRepository;
    private final JornadaRepository jornadaRepository;
    private final LugarRepository lugarRepository;
    private final ProgramaAcademicoRepository programaRepository;
    private final ResultadoRepository resultadoRepository;
    private final TipoVinculoRepository tipoVinculoRepository;
    private final TipoEventoRepository tipoEventoRepository;
    private final CatalogoMapper mapper;

    public CatalogoService(ProgramaAcademicoRepository programaRepository,
                           EntidadPromotoraSaludRepository entidadPromotoraSaludRepository,
                           GeneroRepository generoRepository,
                           DeporteRepository deporteRepository,
                           LugarRepository lugarRepository,
                           FaseRepository faseRepository,
                           JornadaRepository jornadaRepository,
                           GrupoRepository grupoRepository,
                           ResultadoRepository resultadoRepository,
                           TipoVinculoRepository tipoVinculoRepository,
                            TipoEventoRepository tipoEventoRepository,
                           CatalogoMapper mapper) {
        this.programaRepository = programaRepository;
        this.entidadPromotoraSaludRepository = entidadPromotoraSaludRepository;
        this.generoRepository = generoRepository;
        this.deporteRepository = deporteRepository;
        this.lugarRepository = lugarRepository;
        this.faseRepository = faseRepository;
        this.jornadaRepository = jornadaRepository;
        this.grupoRepository = grupoRepository;
        this.resultadoRepository = resultadoRepository;
        this.tipoVinculoRepository = tipoVinculoRepository;
        this.tipoEventoRepository = tipoEventoRepository;
        this.mapper = mapper;
    }

    public List<ProgramaAcademicoDTO> programas() { return programaRepository.findAll().stream().map(mapper::toProgramaAcademicoDTO).toList(); }
    public List<EntidadPromotoraSaludDTO> eps() { return entidadPromotoraSaludRepository.findAll().stream().map(mapper::toEntidadPromotoraSaludDTO).toList(); }
    public List<GeneroDTO> generos() { return generoRepository.findAll().stream().map(mapper::toGeneroDTO).toList(); }
    public List<DeporteDTO> deportes() { return deporteRepository.findAll().stream().map(mapper::toDeporteDTO).toList(); }
    public List<LugarDTO> lugares() { return lugarRepository.findAll().stream().map(mapper::toLugarDTO).toList(); }
    public List<FaseDTO> fases() { return faseRepository.findAll().stream().map(mapper::toFaseDTO).toList(); }
    public List<ResultadoDTO> resultados() { return resultadoRepository.findAll().stream().map(mapper::toResultadoDTO).toList(); }
    public List<TipoVinculoDTO> tiposVinculo() { return tipoVinculoRepository.findAll().stream().map(mapper::toTipoVinculoDTO).toList(); }
    public List<TipoEventoDTO> tiposEvento() { return tipoEventoRepository.findAll().stream().map(mapper::toTipoEventoDTO).toList(); }

    public List<JornadaDTO> jornadasPorTorneo(Integer torneoId) {
        return jornadaRepository.findByTorneoIdOrderByNumeroAsc(torneoId)
                .stream().map(mapper::toJornadaDTO).toList();
    }

    public List<GrupoDTO> gruposPorTorneo(Integer torneoId) {
        return grupoRepository.findByTorneoIdOrderByNombreAsc(torneoId)
                .stream().map(mapper::toGrupoDTO).toList();
    }

    public List<TipoEventoDTO> tiposEventoPorDeporte(Integer deporteId) {
        return tipoEventoRepository.findByDeporteIdOrderByNombreAsc(deporteId)
                .stream().map(mapper::toTipoEventoDTO).toList();
    }
}
