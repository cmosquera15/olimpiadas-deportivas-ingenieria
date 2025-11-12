import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import { IdNombreDTO } from '@/types';

interface OlimpiadaState {
  selectedOlimpiada: IdNombreDTO | null;
  setSelectedOlimpiada: (olimpiada: IdNombreDTO | null) => void;
}

export const useOlimpiada = create<OlimpiadaState>()(
  persist(
    (set) => ({
      selectedOlimpiada: null,
      setSelectedOlimpiada: (olimpiada) => set({ selectedOlimpiada: olimpiada }),
    }),
    {
      name: 'olimpiada-storage',
    }
  )
);
