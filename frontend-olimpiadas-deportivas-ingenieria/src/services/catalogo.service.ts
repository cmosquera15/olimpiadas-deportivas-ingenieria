import axiosInstance from '@/lib/axios';
import {
  ProgramaAcademico,
  EPS,
  Genero,
  Deporte,
  Lugar,
  Fase,
  Jornada,
  Grupo,
  Resultado,
  TipoVinculo,
  TipoEvento,
} from '@/types';

export const catalogoService = {
  getProgramas: async (): Promise<ProgramaAcademico[]> => {
    const { data } = await axiosInstance.get<ProgramaAcademico[]>('/programas');
    return data;
  },

  getEPS: async (): Promise<EPS[]> => {
    const { data } = await axiosInstance.get<EPS[]>('/eps');
    return data;
  },

  getGeneros: async (): Promise<Genero[]> => {
    const { data } = await axiosInstance.get<Genero[]>('/generos');
    return data;
  },

  getDeportes: async (): Promise<Deporte[]> => {
    const { data } = await axiosInstance.get<Deporte[]>('/deportes');
    return data;
  },

  getLugares: async (): Promise<Lugar[]> => {
    const { data } = await axiosInstance.get<Lugar[]>('/lugares');
    return data;
  },

  getFases: async (): Promise<Fase[]> => {
    const { data } = await axiosInstance.get<Fase[]>('/fases');
    return data;
  },

  getJornadas: async (torneoId: number): Promise<Jornada[]> => {
    const { data } = await axiosInstance.get<Jornada[]>('/jornadas', {
      params: { torneoId },
    });
    return data;
  },

  getGrupos: async (torneoId: number): Promise<Grupo[]> => {
    const { data } = await axiosInstance.get<Grupo[]>('/grupos', {
      params: { torneoId },
    });
    return data;
  },

  getResultados: async (): Promise<Resultado[]> => {
    const { data } = await axiosInstance.get<Resultado[]>('/resultados');
    return data;
  },

  getTiposVinculo: async (): Promise<TipoVinculo[]> => {
    const { data } = await axiosInstance.get<TipoVinculo[]>('/tipos-vinculo');
    return data;
  },

  getTiposEvento: async (deporteId?: number): Promise<TipoEvento[]> => {
    const { data } = await axiosInstance.get<TipoEvento[]>('/tipos-evento', {
      params: deporteId ? { deporteId } : undefined,
    });
    return data;
  },
};
