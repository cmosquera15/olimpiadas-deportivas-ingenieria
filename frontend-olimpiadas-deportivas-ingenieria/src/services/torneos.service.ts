import axiosInstance from '@/lib/axios';
import { Torneo, PageResponse, PageRequest } from '@/types';

interface TorneosFilter extends PageRequest {
  activo?: boolean;
  deporteId?: number;
  anio?: number;
}

export const torneosService = {
  getTorneos: async (filters: TorneosFilter = {}): Promise<PageResponse<Torneo>> => {
    const { data } = await axiosInstance.get<PageResponse<Torneo>>('/torneos', {
      params: filters,
    });
    return data;
  },

  getTorneo: async (id: number): Promise<Torneo> => {
    const { data } = await axiosInstance.get<Torneo>(`/torneos/${id}`);
    return data;
  },

  generarLlaves: async (id: number): Promise<void> => {
    await axiosInstance.post(`/torneos/${id}/generar-llaves`);
  },
};
