import axiosInstance from '@/lib/axios';
import { Evento, EventoCreateRequest } from '@/types';

export const eventosService = {
  getEventos: async (partidoId: number): Promise<Evento[]> => {
    const { data } = await axiosInstance.get<Evento[]>('/eventos', {
      params: { partidoId },
    });
    return data;
  },

  createEvento: async (request: EventoCreateRequest): Promise<Evento> => {
    const { data } = await axiosInstance.post<Evento>('/eventos', request);
    return data;
  },

  deleteEvento: async (id: number): Promise<void> => {
    await axiosInstance.delete(`/eventos/${id}`);
  },
};
