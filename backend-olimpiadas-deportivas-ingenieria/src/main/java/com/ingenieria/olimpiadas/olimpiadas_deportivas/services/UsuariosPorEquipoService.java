package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions.BadRequestException;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions.NotFoundException;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Equipo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.UsuariosPorEquipo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.usuario.Usuario;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo.EquipoRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo.TorneoRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo.UsuariosPorEquipoRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.usuario.UsuarioRepository;

import java.util.List;

@Service
public class UsuariosPorEquipoService {

    private final UsuariosPorEquipoRepository upeRepo;
    private final EquipoRepository equipoRepo;
    private final TorneoRepository torneoRepo;
    private final UsuarioRepository usuarioRepo;
    private final ReglaDeporteService reglaService;

    public UsuariosPorEquipoService(UsuariosPorEquipoRepository upeRepo,
                                   EquipoRepository equipoRepo,
                                   TorneoRepository torneoRepo,
                                   UsuarioRepository usuarioRepo,
                                   ReglaDeporteService reglaService) {
        this.upeRepo = upeRepo;
        this.equipoRepo = equipoRepo;
        this.torneoRepo = torneoRepo;
        this.usuarioRepo = usuarioRepo;
        this.reglaService = reglaService;
    }

    @Transactional
    public UsuariosPorEquipo agregarJugador(Integer equipoId, Integer usuarioId, Integer torneoId) {
        Equipo equipo = equipoRepo.findById(equipoId)
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));
        Torneo torneo = torneoRepo.findById(torneoId)
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));
        if (!equipo.getTorneo().getId().equals(torneo.getId())) {
            throw new BadRequestException("El equipo no pertenece al torneo indicado");
        }
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (usuario.getHabilitado() != null && !usuario.getHabilitado()) {
            throw new BadRequestException("Usuario no habilitado para participar");
        }
        String rolNombre = (usuario.getRol() != null ? usuario.getRol().getNombre() : "").toUpperCase();
        if (!"JUGADOR".equals(rolNombre)) {
            throw new BadRequestException("Solo usuarios con rol JUGADOR pueden ser inscritos en un equipo");
        }

        Integer deporteId = equipo.getTorneo().getDeporte() != null ? equipo.getTorneo().getDeporte().getId() : null;
        if (deporteId != null && upeRepo.existsUsuarioEnOtroEquipoMismaDisciplinaMismoTorneo(
                usuario.getId(), torneo.getId(), equipo.getId(), deporteId)) {
            throw new BadRequestException("El usuario ya está inscrito en otro equipo de la misma disciplina para este torneo");
        }

        UsuariosPorEquipo nuevo = new UsuariosPorEquipo();
        nuevo.setUsuario(usuario);
        nuevo.setEquipo(equipo);
        nuevo.setTorneo(torneo);

        try {
            UsuariosPorEquipo saved = upeRepo.save(nuevo);

            long total = upeRepo.countByEquipoIdAndTorneoId(equipoId, torneoId);
            long mujeres = upeRepo.countMujeresEnEquipoTorneo(equipoId, torneoId);
            reglaService.validarPlantillaMinimos(equipo, total, mujeres);

            return saved;
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("El usuario ya está inscrito en este equipo/torneo");
        }
    }

    /**
     * Eliminar jugador SIN romper reglas:
     * - Si al eliminar quedas por debajo del mínimo => BLOQUEA
     * - Si al eliminar se queda sin mujeres y el deporte lo exige => BLOQUEA
     */
    @Transactional
    public void removerJugador(Integer usuariosPorEquipoId) {
        UsuariosPorEquipo upe = upeRepo.findById(usuariosPorEquipoId)
                .orElseThrow(() -> new NotFoundException("Registro de usuario por equipo no encontrado"));

        Integer equipoId = upe.getEquipo().getId();
        Integer torneoId = upe.getTorneo().getId();

        Equipo equipo = upe.getEquipo();

        // Conteos actuales
        long totalAntes = upeRepo.countByEquipoIdAndTorneoId(equipoId, torneoId);
        long mujeresAntes = upeRepo.countMujeresEnEquipoTorneo(equipoId, torneoId);

        boolean esMujer = upe.getUsuario() != null
                && upe.getUsuario().getGenero() != null
                && upe.getUsuario().getGenero().getNombre() != null
                && upe.getUsuario().getGenero().getNombre().toUpperCase().startsWith("F");

        int min = reglaService.minJugadores(equipo);
        long totalDespues   = totalAntes - 1;
        long mujeresDespues = esMujer ? (mujeresAntes - 1) : mujeresAntes;

        if (totalDespues < min) {
            throw new BadRequestException("No se puede eliminar: el equipo quedaría por debajo del mínimo (" + min + ")");
        }
        if (reglaService.requiereMujer(equipo) && mujeresDespues <= 0) {
            throw new BadRequestException("No se puede eliminar: debe quedar al menos una mujer en la plantilla.");
        }

        upeRepo.delete(upe);
    }

    /** Listado eficiente con query dedicada */
    @Transactional
    public List<UsuariosPorEquipo> listarPorEquipo(Integer equipoId, Integer torneoId) {
        return upeRepo.findByEquipoAndTorneo(equipoId, torneoId);
    }
}
