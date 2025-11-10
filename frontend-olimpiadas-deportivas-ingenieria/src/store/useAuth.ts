import { create } from 'zustand';
import { Usuario } from '@/types';
import { getToken, clearToken, setToken as saveToken, parseJWT } from '@/lib/auth';

interface AuthState {
  user: Usuario | null;
  token: string | null;
  isAuthenticated: boolean;
  setAuth: (token: string, user: Usuario) => void;
  clearAuth: () => void;
  initAuth: () => void;
}

export const useAuth = create<AuthState>((set) => ({
  user: null,
  token: null,
  isAuthenticated: false,

  setAuth: (token: string, user: Usuario) => {
    saveToken(token);
    set({ user, token, isAuthenticated: true });
  },

  clearAuth: () => {
    clearToken();
    set({ user: null, token: null, isAuthenticated: false });
  },

  initAuth: () => {
    const token = getToken();
    if (token) {
      const payload = parseJWT(token);
      if (payload && Date.now() < payload.exp * 1000) {
        set({ token, isAuthenticated: true });
      } else {
        clearToken();
      }
    }
  },
}));
