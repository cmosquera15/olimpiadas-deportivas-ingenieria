import axiosInstance from '@/lib/axios';
import { Equipo, UsuarioPorEquipo } from '@/types';

export const equiposService = {
  getEquipos: async (torneoId?: number): Promise<Equipo[]> => {
    const { data } = await axiosInstance.get<Equipo[]>('/equipos', {
      params: torneoId ? { torneoId } : undefined,
    });
    return data;
  },

  getEquipo: async (id: number): Promise<Equipo> => {
    const { data } = await axiosInstance.get<Equipo>(`/equipos/${id}`);
    return data;
  },

  createEquipo: async (request: { nombre: string; torneoId: number }): Promise<Equipo> => {
    const { data } = await axiosInstance.post<Equipo>('/equipos', request);
    return data;
  },

  updateEquipo: async (
    id: number,
    request: { nombre: string; torneoId: number }
  ): Promise<Equipo> => {
    const { data } = await axiosInstance.put<Equipo>(`/equipos/${id}`, request);
    return data;
  },

  deleteEquipo: async (id: number): Promise<void> => {
    await axiosInstance.delete(`/equipos/${id}`);
  },

  getPlantilla: async (equipoId: number, torneoId: number): Promise<UsuarioPorEquipo[]> => {
    const { data } = await axiosInstance.get<UsuarioPorEquipo[]>(
      `/equipos/${equipoId}/plantilla`,
      {
        params: { torneoId },
      }
    );
    return data;
  },

  addToPlantilla: async (
    equipoId: number,
    usuarioId: number,
    torneoId: number
  ): Promise<UsuarioPorEquipo> => {
    const { data } = await axiosInstance.post<UsuarioPorEquipo>(
      `/equipos/${equipoId}/plantilla`,
      null,
      {
        params: { usuarioId, torneoId },
      }
    );
    return data;
  },

  removeFromPlantilla: async (usuariosPorEquipoId: number): Promise<void> => {
    await axiosInstance.delete(`/equipos/plantilla/${usuariosPorEquipoId}`);
  },
};
