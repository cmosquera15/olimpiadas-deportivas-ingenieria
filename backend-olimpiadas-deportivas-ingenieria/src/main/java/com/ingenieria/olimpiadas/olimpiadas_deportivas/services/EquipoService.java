package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo.*;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions.*;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.mappers.EquipoMapper;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.*;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.*;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo.*;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo.*;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.usuario.UsuarioRepository;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final TorneoRepository torneoRepository;
    private final GrupoRepository grupoRepository;
    private final ProgramaAcademicoRepository programaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EquipoMapper mapper;

    public EquipoService(EquipoRepository equipoRepository,
                         TorneoRepository torneoRepository,
                         GrupoRepository grupoRepository,
                         ProgramaAcademicoRepository programaRepository,
                         UsuarioRepository usuarioRepository,
                         EquipoMapper mapper) {
        this.equipoRepository = equipoRepository;
        this.torneoRepository = torneoRepository;
        this.grupoRepository = grupoRepository;
        this.programaRepository = programaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    public Page<EquipoListDTO> listar(Integer torneoId, Integer grupoId, Pageable pageable) {
        List<Equipo> base;
        if (torneoId != null && grupoId != null) {
            base = grupoRepository.findById(grupoId)
                    .map(g -> equipoRepository.findByGrupo(grupoId))
                    .orElse(List.of());
        } else if (torneoId != null) {
            base = equipoRepository.findByTorneoIdOrderByNombreAsc(torneoId);
        } else {
            base = equipoRepository.findAll();
        }

        List<EquipoListDTO> dtos = base.stream().map(mapper::toListDTO).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), dtos.size());
        List<EquipoListDTO> slice = start > end ? List.of() : dtos.subList(start, end);

        return new PageImpl<>(slice, pageable, dtos.size());
    }

    public EquipoDetailDTO detalle(Integer id) {
        Equipo e = equipoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));
        return mapper.toDetailDTO(e);
    }

    @Transactional
    public EquipoDetailDTO crear(EquipoCreateDTO req) {
        Torneo torneo = torneoRepository.findById(req.getId_torneo())
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));
        Grupo grupo = grupoRepository.findById(req.getId_grupo())
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        if (!grupo.getTorneo().getId().equals(torneo.getId())) {
            throw new BadRequestException("El grupo no pertenece al torneo");
        }

        var p1 = programaRepository.findById(req.getId_programa_academico_1())
                .orElseThrow(() -> new NotFoundException("Programa 1 no encontrado"));
        var p2 = (req.getId_programa_academico_2() == null) ? null :
                programaRepository.findById(req.getId_programa_academico_2())
                        .orElseThrow(() -> new NotFoundException("Programa 2 no encontrado"));

        Usuario cap = null;
        if (req.getId_usuario_capitan() != null) {
            cap = usuarioRepository.findById(req.getId_usuario_capitan())
                    .orElseThrow(() -> new NotFoundException("Capitán no encontrado"));
        }

        Equipo e = new Equipo();
        e.setNombre(req.getNombre());
        e.setTorneo(torneo);
        e.setGrupo(grupo);
        e.setProgramaAcademico1(p1);
        e.setProgramaAcademico2(p2);
        e.setCapitan(cap);

        e = equipoRepository.save(e);
        return mapper.toDetailDTO(e);
    }

    @Transactional
    public EquipoDetailDTO actualizar(Integer id, EquipoUpdateDTO req) {
        Equipo e = equipoRepository.findById(id).orElseThrow(() -> new NotFoundException("Equipo no encontrado"));

        if (req.getNombre() != null) e.setNombre(req.getNombre());

        if (req.getId_grupo() != null) {
            Grupo g = grupoRepository.findById(req.getId_grupo())
                    .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
            if (!g.getTorneo().getId().equals(e.getTorneo().getId()))
                throw new BadRequestException("El grupo no pertenece al torneo del equipo");
            e.setGrupo(g);
        }
        if (req.getId_programa_academico_1() != null) {
            e.setProgramaAcademico1(programaRepository.findById(req.getId_programa_academico_1())
                    .orElseThrow(() -> new NotFoundException("Programa 1 no encontrado")));
        }
        if (req.getId_programa_academico_2() != null) {
            e.setProgramaAcademico2(programaRepository.findById(req.getId_programa_academico_2())
                    .orElseThrow(() -> new NotFoundException("Programa 2 no encontrado")));
        }
        if (req.getId_usuario_capitan() != null) {
            e.setCapitan(usuarioRepository.findById(req.getId_usuario_capitan())
                    .orElseThrow(() -> new NotFoundException("Capitán no encontrado")));
        }

        Equipo saved = equipoRepository.save(e);

        return mapper.toDetailDTO(saved);
    }

    @Transactional
    public void eliminar(Integer id) {
        equipoRepository.deleteById(id);
    }
}
