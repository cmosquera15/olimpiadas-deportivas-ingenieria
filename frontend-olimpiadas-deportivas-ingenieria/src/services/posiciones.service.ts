import axiosInstance from '@/lib/axios';
import { Posicion } from '@/types';

export const posicionesService = {
  getPosiciones: async (torneoId: number, grupoId?: number): Promise<Posicion[]> => {
    const { data } = await axiosInstance.get<Posicion[]>('/posiciones', {
      params: { torneoId, grupoId },
    });
    return data;
  },
};
