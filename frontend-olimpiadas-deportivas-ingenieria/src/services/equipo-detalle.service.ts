import axiosInstance from '@/lib/axios';
import { Equipo, Usuario, PageResponse } from '@/types';

// Shape del DTO que retorna el backend para detalle de equipo
interface EquipoDetailBackendDTO {
  id: number;
  nombre: string;
  id_torneo: number | null;
  torneoNombre: string | null;
  id_grupo: number | null;
  grupoNombre: string | null;
  id_programa_academico_1: number;
  programa1Nombre: string | null;
  id_programa_academico_2: number | null;
  programa2Nombre: string | null;
  id_usuario_capitan: number | null;
  capitanNombre: string | null;
}

export const equipoDetalleService = {
  getEquipoDetalle: async (id: number): Promise<Equipo> => {
    const { data } = await axiosInstance.get<EquipoDetailBackendDTO>(`/equipos/${id}`);

    return {
      id: data.id,
      nombre: data.nombre,
      torneoId: data.id_torneo ?? undefined,
      torneoNombre: data.torneoNombre ?? undefined,
      programaAcademico1: {
        id: data.id_programa_academico_1,
        nombre: data.programa1Nombre ?? 'Programa 1',
      },
      programaAcademico2: data.id_programa_academico_2
        ? { id: data.id_programa_academico_2, nombre: data.programa2Nombre ?? 'Programa 2' }
        : undefined,
      capitanId: data.id_usuario_capitan ?? undefined,
      capitan: data.id_usuario_capitan
        ? {
            id: data.id_usuario_capitan,
            nombre: data.capitanNombre ?? null,
            correo: null,
            rol: 'JUGADOR',
            completo: true,
            habilitado: true,
          }
        : undefined,
      integrantesCount: 0,
      createdAt: '',
      updatedAt: '',
    };
  },

  getJugadoresDisponibles: async (
    equipoId: number,
    torneoId: number,
    q?: string
  ): Promise<Usuario[]> => {
    const { data } = await axiosInstance.get<PageResponse<{
      id: number;
      nombre: string;
      correo: string;
      documento?: string;
      programaId?: number;
      programaNombre?: string;
      generoNombre?: string;
    }>>(`/equipos/${equipoId}/candidatos`, {
      params: { torneoId, q, size: 50 },
    });
    // Map minimal fields to Usuario type for current UI needs
    return data.content.map((u) => ({
      id: u.id,
      nombre: u.nombre,
      correo: u.correo,
      documento: u.documento,
      rol: 'JUGADOR',
      completo: true,
      habilitado: true,
      programaAcademico: u.programaId && u.programaNombre ? { id: u.programaId, nombre: u.programaNombre } : undefined,
      genero: u.generoNombre ? { id: 0, nombre: u.generoNombre } : undefined,
    }));
  },

  actualizarPlantilla: async (
    equipoId: number,
    plantilla: { usuarioId: number; posicionId?: number; esCapitan?: boolean }[]
  ): Promise<unknown[]> => {
    const { data } = await axiosInstance.put<unknown[]>(
      `/equipos/${equipoId}/plantilla`,
      plantilla
    );
    return data;
  },

  actualizarEstadoJugador: async (
    equipoId: number,
    usuarioId: number,
    estaActivo: boolean
  ): Promise<void> => {
    await axiosInstance.patch(`/equipos/${equipoId}/plantilla/${usuarioId}/estado`, {
      estaActivo,
    });
  },

  asignarCapitan: async (equipoId: number, usuarioId: number): Promise<void> => {
    await axiosInstance.post(`/equipos/${equipoId}/capitan/${usuarioId}`);
  },
};