import axiosInstance from '@/lib/axios';
import { TorneoListDTO, TorneoDetailDTO, TorneoCreateRequest, IdNombreDTO, PageResponse, PageRequest } from '@/types';

interface TorneosFilter extends PageRequest {
  olimpiadaId?: number;
  deporteId?: number;
}

export const torneosService = {
  getTorneos: async (filters: TorneosFilter = {}): Promise<PageResponse<TorneoListDTO>> => {
    const { data } = await axiosInstance.get<PageResponse<TorneoListDTO>>('/torneos', {
      params: filters,
    });
    return data;
  },

  getTorneo: async (id: number): Promise<TorneoDetailDTO> => {
    const { data } = await axiosInstance.get<TorneoDetailDTO>(`/torneos/${id}`);
    return data;
  },

  getTorneosOpciones: async (olimpiadaId?: number, deporteId?: number): Promise<IdNombreDTO[]> => {
    const { data } = await axiosInstance.get<IdNombreDTO[]>('/torneos/opciones', {
      params: { olimpiadaId, deporteId },
    });
    return data;
  },

  createTorneo: async (request: TorneoCreateRequest): Promise<TorneoDetailDTO> => {
    const { data } = await axiosInstance.post<TorneoDetailDTO>('/torneos', request);
    return data;
  },

  generarLlaves: async (id: number): Promise<void> => {
    await axiosInstance.post(`/torneos/${id}/generar-llaves`);
  },
};
