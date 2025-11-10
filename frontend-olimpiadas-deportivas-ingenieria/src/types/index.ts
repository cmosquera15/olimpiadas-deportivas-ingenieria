// Auth types
export interface AuthDTO {
  jwt: string;
  completo: boolean;
  usuario?: Usuario;
}

export interface CompletarPerfilRequest {
  documento: string;
  id_eps: number;
  id_programa_academico: number;
  id_genero: number;
  id_tipo_vinculo: number;
}

// Usuario types
export interface Usuario {
  id: number;
  nombre: string;
  email: string;
  documento?: string;
  rol: string;
  habilitado: boolean;
  completo: boolean;
  genero?: Genero;
  eps?: EPS;
  programaAcademico?: ProgramaAcademico;
  tipoVinculo?: TipoVinculo;
}

// Catálogo types
export interface ProgramaAcademico {
  id: number;
  nombre: string;
}

export interface EPS {
  id: number;
  nombre: string;
}

export interface Genero {
  id: number;
  nombre: string;
}

export interface Deporte {
  id: number;
  nombre: string;
}

export interface Lugar {
  id: number;
  nombre: string;
  capacidad?: number;
}

export interface Fase {
  id: number;
  nombre: string;
  orden: number;
}

export interface Jornada {
  id: number;
  nombre: string;
  torneoId: number;
}

export interface Grupo {
  id: number;
  nombre: string;
  torneoId: number;
}

export interface Resultado {
  id: number;
  nombre: string;
  codigo: string;
}

export interface TipoVinculo {
  id: number;
  nombre: string;
}

export interface TipoEvento {
  id: number;
  nombre: string;
  deporteId: number;
  puntosNegativos: number;
}

// Torneo types
export interface Torneo {
  id: number;
  nombre: string;
  anio: number;
  activo: boolean;
  deporte: Deporte;
  reglamentoUrl?: string;
  createdAt: string;
  updatedAt: string;
}

// Equipo types
export interface Equipo {
  id: number;
  nombre: string;
  torneoId: number;
  torneo?: Torneo;
  createdAt: string;
  updatedAt: string;
}

export interface UsuarioPorEquipo {
  id: number;
  equipoId: number;
  usuarioId: number;
  torneoId: number;
  usuario: Usuario;
  equipo: Equipo;
}

// Partido types
export interface Partido {
  id: number;
  torneoId: number;
  faseId?: number;
  grupoId?: number;
  jornadaId?: number;
  lugarId: number;
  arbitroId?: number;
  fecha: string;
  hora: string;
  observaciones?: string;
  puntosEquipo1?: number;
  puntosEquipo2?: number;
  resultadoEquipo1Id?: number;
  resultadoEquipo2Id?: number;
  torneo: Torneo;
  fase?: Fase;
  grupo?: Grupo;
  jornada?: Jornada;
  lugar: Lugar;
  arbitro?: Usuario;
  equipo1?: Equipo;
  equipo2?: Equipo;
  resultadoEquipo1?: Resultado;
  resultadoEquipo2?: Resultado;
}

export interface PartidoCreateRequest {
  torneoId: number;
  faseId?: number;
  grupoId?: number;
  jornadaId?: number;
  lugarId: number;
  arbitroId?: number;
  fecha: string;
  hora: string;
  observaciones?: string;
}

export interface AsignarEquiposRequest {
  equipoId1: number;
  equipoId2: number;
}

export interface ActualizarMarcadorRequest {
  puntosEquipo1?: number;
  puntosEquipo2?: number;
  resultadoEquipo1Id?: number;
  resultadoEquipo2Id?: number;
}

// Evento types
export interface Evento {
  id: number;
  partidoId: number;
  tipoEventoId: number;
  usuarioId: number;
  minuto?: number;
  observaciones?: string;
  tipoEvento: TipoEvento;
  usuario: Usuario;
}

export interface EventoCreateRequest {
  partidoId: number;
  tipoEventoId: number;
  usuarioId: number;
  minuto?: number;
  observaciones?: string;
}

// Posiciones types
export interface Posicion {
  equipo: Equipo;
  pj: number;
  pg: number;
  pe: number;
  pp: number;
  gf: number;
  gc: number;
  gd: number;
  pts: number;
  fairPlay: number;
}

// Pagination types
export interface PageRequest {
  page?: number;
  size?: number;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
