import { Equipo, Usuario } from '.'

export interface RosterPosition {
  id: number;
  nombre: string;
}

export interface TeamStats {
  partidosJugados: number;
  partidosGanados: number;
  partidosEmpatados: number;
  partidosPerdidos: number;
  golesFavor: number;
  golesContra: number;
  diferenciaGoles: number;
  puntos: number;
  fairPlayPoints: number;
}

export interface RosterMember {
  id: number;
  usuario: Usuario;
  posicion?: RosterPosition;
  estaActivo: boolean;
  esCapitan: boolean;
}

export interface EquipoDetalle extends Equipo {
  plantilla: RosterMember[];
  stats: TeamStats;
}