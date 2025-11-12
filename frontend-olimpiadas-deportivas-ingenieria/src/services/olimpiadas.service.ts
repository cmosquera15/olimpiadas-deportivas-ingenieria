import axiosInstance from '@/lib/axios';
import { IdNombreDTO } from '@/types';

export interface OlimpiadaCreateRequest {
  nombre: string;
  edicion: number;
  anio: number;
  activo?: boolean;
}

export interface OlimpiadaUpdateRequest {
  nombre: string;
  edicion: number;
  anio: number;
  activo?: boolean;
}

export interface OlimpiadaDetailResponse {
  id: number;
  nombre: string;
  slug: string;
  edicion: number;
  anio: number;
  activo: boolean;
  torneos: {
    id: number;
    nombre: string;
    deporteId: number;
    deporteNombre: string;
  }[];
}

export const olimpiadasService = {
  getOlimpiadas: async (): Promise<IdNombreDTO[]> => {
    const { data } = await axiosInstance.get<IdNombreDTO[]>('/olimpiadas');
    return data;
  },

  getOlimpiadasTodas: async (): Promise<OlimpiadaDetailResponse[]> => {
    const { data } = await axiosInstance.get<OlimpiadaDetailResponse[]>('/olimpiadas/todas');
    return data;
  },

  getOlimpiadaById: async (id: number): Promise<OlimpiadaDetailResponse> => {
    const { data } = await axiosInstance.get<OlimpiadaDetailResponse>(`/olimpiadas/${id}`);
    return data;
  },

  createOlimpiada: async (request: OlimpiadaCreateRequest): Promise<OlimpiadaDetailResponse> => {
    const { data } = await axiosInstance.post<OlimpiadaDetailResponse>('/olimpiadas', request);
    return data;
  },

  updateOlimpiada: async (id: number, request: OlimpiadaUpdateRequest): Promise<OlimpiadaDetailResponse> => {
    const { data } = await axiosInstance.put<OlimpiadaDetailResponse>(`/olimpiadas/${id}`, request);
    return data;
  },
};
