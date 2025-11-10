import axiosInstance from '@/lib/axios';
import {
  Partido,
  PartidoCreateRequest,
  AsignarEquiposRequest,
  ActualizarMarcadorRequest,
  PageResponse,
  PageRequest,
} from '@/types';

interface PartidosFilter extends PageRequest {
  torneoId?: number;
  faseId?: number;
  grupoId?: number;
  arbitroId?: number;
}

export const partidosService = {
  getPartidos: async (filters: PartidosFilter = {}): Promise<PageResponse<Partido>> => {
    const { data } = await axiosInstance.get<PageResponse<Partido>>('/partidos', {
      params: filters,
    });
    return data;
  },

  getPartido: async (id: number): Promise<Partido> => {
    const { data } = await axiosInstance.get<Partido>(`/partidos/${id}`);
    return data;
  },

  createPartido: async (request: PartidoCreateRequest): Promise<Partido> => {
    const { data } = await axiosInstance.post<Partido>('/partidos', request);
    return data;
  },

  updatePartido: async (id: number, request: PartidoCreateRequest): Promise<Partido> => {
    const { data } = await axiosInstance.put<Partido>(`/partidos/${id}`, request);
    return data;
  },

  deletePartido: async (id: number): Promise<void> => {
    await axiosInstance.delete(`/partidos/${id}`);
  },

  asignarEquipos: async (id: number, request: AsignarEquiposRequest): Promise<Partido> => {
    const { data } = await axiosInstance.post<Partido>(`/partidos/${id}/asignar-equipos`, request);
    return data;
  },

  actualizarMarcador: async (
    id: number,
    request: ActualizarMarcadorRequest
  ): Promise<Partido> => {
    const { data } = await axiosInstance.put<Partido>(`/partidos/${id}/marcador`, request);
    return data;
  },
};
