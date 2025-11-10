package com.ingenieria.olimpiadas.olimpiadas_deportivas.services;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.equipo.EquipoPosicionDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.torneo.TablaPosicionesDTO;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.exceptions.NotFoundException;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.catalogo.Resultado;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.EquiposPorPartido;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Partido;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.models.torneo.Torneo;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.catalogo.GrupoRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.evento.EventoRepository;
import com.ingenieria.olimpiadas.olimpiadas_deportivas.repositories.torneo.*;

@Service
public class TablaPosicionesService {

    private final TorneoRepository torneoRepository;
    private final GrupoRepository grupoRepository;
    private final PartidoRepository partidoRepository;
    private final EquiposPorPartidoRepository eppRepository;
    private final EventoRepository eventoRepository;

    public TablaPosicionesService(TorneoRepository torneoRepository,
                                  GrupoRepository grupoRepository,
                                  PartidoRepository partidoRepository,
                                  EquiposPorPartidoRepository eppRepository,
                                  EventoRepository eventoRepository) {
        this.torneoRepository = torneoRepository;
        this.grupoRepository = grupoRepository;
        this.partidoRepository = partidoRepository;
        this.eppRepository = eppRepository;
        this.eventoRepository = eventoRepository;
    }

    public TablaPosicionesDTO calcular(Integer torneoId, Integer grupoIdOrNull) {
        Torneo torneo = torneoRepository.findById(torneoId)
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));

        var grupo = (grupoIdOrNull == null) ? null
                : grupoRepository.findById(grupoIdOrNull)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));

        List<Partido> partidos = partidoRepository.findByTorneoOrdered(torneoId);
        if (grupo != null) {
            partidos = partidos.stream()
                    .filter(p -> p.getGrupo() != null && grupo.getId().equals(p.getGrupo().getId()))
                    .toList();
        }

        record Stats(int equipoId, String nombre,
                     AtomicInteger pj, AtomicInteger pg, AtomicInteger pe, AtomicInteger pp, AtomicInteger wo,
                     AtomicInteger gf, AtomicInteger gc, AtomicInteger pts,
                     AtomicInteger cestasPrimerPartido) {

            static Stats of(int id, String nombre) {
                return new Stats(id, nombre,
                        new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), new AtomicInteger(), new AtomicInteger(),
                        new AtomicInteger(), new AtomicInteger(), new AtomicInteger(),
                        new AtomicInteger());
            }
        }

        Map<Integer, Stats> map = new HashMap<>();

        String depName = Optional.ofNullable(torneo.getDeporte())
                .map(d -> d.getNombre() == null ? "" : d.getNombre().toUpperCase())
                .orElse("");

        boolean esBasket = depName.contains("BALONCESTO");
        boolean esFutbol = depName.contains("FUTBOL") || depName.contains("FÚTBOL");

        int ptsWin   = esBasket ? 2 : 3;
        int ptsDraw  = esBasket ? 0 : 1;
        int ptsWO    = 0;

        for (Partido p : partidos) {
            List<EquiposPorPartido> epps = eppRepository.findByPartidoId(p.getId());
            if (epps.size() != 2) continue;

            var a = epps.get(0);
            var b = epps.get(1);

            Integer aId = a.getEquipo().getId();
            Integer bId = b.getEquipo().getId();
            map.putIfAbsent(aId, Stats.of(aId, a.getEquipo().getNombre()));
            map.putIfAbsent(bId, Stats.of(bId, b.getEquipo().getNombre()));
            var sa = map.get(aId);
            var sb = map.get(bId);

            int pa = Optional.ofNullable(a.getPuntos()).orElse(0);
            int pb = Optional.ofNullable(b.getPuntos()).orElse(0);

            sa.pj.incrementAndGet();
            sb.pj.incrementAndGet();
            sa.gf.addAndGet(pa);
            sa.gc.addAndGet(pb);
            sb.gf.addAndGet(pb);
            sb.gc.addAndGet(pa);

            String resA = Optional.ofNullable(a.getResultado()).map(Resultado::getNombre).orElse(null);
            String resB = Optional.ofNullable(b.getResultado()).map(Resultado::getNombre).orElse(null);
            boolean aWO = resA != null && resA.equalsIgnoreCase("WO");
            boolean bWO = resB != null && resB.equalsIgnoreCase("WO");

            if (aWO ^ bWO) { // uno solo WO
                if (aWO) { sa.wo.incrementAndGet(); sa.pp.incrementAndGet(); sb.pg.incrementAndGet(); sb.pts.addAndGet(esBasket ? 2 : 3); }
                else     { sb.wo.incrementAndGet(); sb.pp.incrementAndGet(); sa.pg.incrementAndGet(); sa.pts.addAndGet(esBasket ? 2 : 3); }
                continue;
            }

            if (pa > pb) { sa.pg.incrementAndGet(); sb.pp.incrementAndGet(); sa.pts.addAndGet(ptsWin); }
            else if (pa < pb) { sb.pg.incrementAndGet(); sa.pp.incrementAndGet(); sb.pts.addAndGet(ptsWin); }
            else {
                sa.pe.incrementAndGet();
                sb.pe.incrementAndGet();
                sa.pts.addAndGet(ptsDraw);
                sb.pts.addAndGet(ptsDraw);
            }

            if (esBasket) {
                if (sa.cestasPrimerPartido.get() == 0) sa.cestasPrimerPartido.set(pa);
                if (sb.cestasPrimerPartido.get() == 0) sb.cestasPrimerPartido.set(pb);
            }
        }

        for (var st : map.values()) {
            int totalNeg = eventoRepository
                    .sumPuntosNegativosEquipoEnFase(torneoId, "Grupo", st.equipoId);
        }

        Comparator<EquipoPosicionDTO> futbolCmp = Comparator
                .comparing(EquipoPosicionDTO::fairPlay)             // 1) menor
                .thenComparing(EquipoPosicionDTO::pg, Comparator.reverseOrder()) // 2)
                .thenComparing(dto -> dto.gf() - dto.gc(), Comparator.reverseOrder()) // 3) DG
                .thenComparing(EquipoPosicionDTO::gf, Comparator.reverseOrder()) // 4)
                .thenComparing(EquipoPosicionDTO::pp)               // 5)
                .thenComparing(EquipoPosicionDTO::gc);              // 6)

        Comparator<EquipoPosicionDTO> basketCmp = Comparator
                .comparing(EquipoPosicionDTO::fairPlay)             // 1)
                .thenComparing(EquipoPosicionDTO::pg, Comparator.reverseOrder()) // 2)
                .thenComparing(EquipoPosicionDTO::gf, Comparator.reverseOrder()) // 3) cestas a favor
                .thenComparing(dto -> dto.gf() - dto.gc(), Comparator.reverseOrder()); // 4) diff cestas

        Map<Integer, Integer> primerJuegoCestas = map.values().stream()
                .collect(Collectors.toMap(s -> s.equipoId, s -> s.cestasPrimerPartido.get()));

        List<EquipoPosicionDTO> posiciones = map.values().stream().map(s -> {
            int gd = s.gf.get() - s.gc.get();
            int totalNeg = eventoRepository
                    .sumPuntosNegativosEquipoEnFase(torneoId, "Grupo", s.equipoId);
            double fair = s.pj.get() > 0 ? (totalNeg * 1.0 / s.pj.get()) : 0.0;

            return new EquipoPosicionDTO(
                    s.equipoId, s.nombre,
                    s.pj.get(), s.pg.get(), s.pe.get(), s.pp.get(),
                    s.gf.get(), s.gc.get(), gd, s.pts.get(), fair
            );
        }).collect(Collectors.toList());

        posiciones.sort(Comparator.comparing(EquipoPosicionDTO::pts).reversed());

        Comparator<EquipoPosicionDTO> tieBreak = esBasket ? basketCmp : futbolCmp;
        posiciones = posiciones.stream()
                .sorted(Comparator.comparing(EquipoPosicionDTO::pts).reversed()
                        .thenComparing(tieBreak)
                        .thenComparing(dto -> esBasket ? -primerJuegoCestas.getOrDefault(dto.equipoId(), 0) : 0))
                .toList();

        return new TablaPosicionesDTO(
                torneo.getId(),
                torneo.getNombre(),
                grupo != null ? grupo.getId() : null,
                grupo != null ? grupo.getNombre() : null,
                posiciones
        );
    }
}
