import axiosInstance from '@/lib/axios';
import { Usuario, PageResponse, PageRequest } from '@/types';

interface UsuariosFilter extends PageRequest {
  search?: string;
}

export const usuariosService = {
  getUsuario: async (id: number): Promise<Usuario> => {
    const { data } = await axiosInstance.get<Usuario>(`/usuarios/${id}`);
    return data;
  },

  getUsuarios: async (filters: UsuariosFilter = {}): Promise<PageResponse<Usuario>> => {
    const { data } = await axiosInstance.get<PageResponse<Usuario>>('/admin/usuarios', {
      params: filters,
    });
    return data;
  },

  updateRol: async (id: number, rol: string): Promise<Usuario> => {
    const { data } = await axiosInstance.put<Usuario>(`/admin/usuarios/${id}/rol`, { rol });
    return data;
  },

  updateHabilitado: async (id: number, habilitado: boolean): Promise<Usuario> => {
    const { data } = await axiosInstance.put<Usuario>(`/admin/usuarios/${id}/habilitado`, {
      habilitado,
    });
    return data;
  },
};
