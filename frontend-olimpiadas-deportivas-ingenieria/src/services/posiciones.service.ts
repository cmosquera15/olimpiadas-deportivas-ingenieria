import axiosInstance from '@/lib/axios';
import { TablaPosiciones } from '@/types';

export const posicionesService = {
  getTabla: async (torneoId: number, grupoId?: number): Promise<TablaPosiciones> => {
    const { data } = await axiosInstance.get<TablaPosiciones>('/posiciones', {
      params: { torneoId, grupoId },
    });
    console.log('🔍 RAW API Response:', data);
    console.log('🔍 Posiciones array:', data.posiciones);
    console.log('🔍 First team name:', data.posiciones?.[0]?.equipoNombre);
    return data;
  },
};
