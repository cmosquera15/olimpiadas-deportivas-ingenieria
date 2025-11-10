import axiosInstance from '@/lib/axios';
import { AuthDTO, CompletarPerfilRequest } from '@/types';

export const authService = {
  googleLogin: async (token: string): Promise<AuthDTO> => {
    const { data } = await axiosInstance.post<AuthDTO>('/auth/google-login', { token });
    return data;
  },

  completarPerfil: async (request: CompletarPerfilRequest): Promise<AuthDTO> => {
    const { data } = await axiosInstance.post<AuthDTO>('/auth/completar-perfil', request);
    return data;
  },

  getMe: async (): Promise<AuthDTO> => {
    const { data } = await axiosInstance.get<AuthDTO>('/usuarios/me');
    return data;
  },
};
