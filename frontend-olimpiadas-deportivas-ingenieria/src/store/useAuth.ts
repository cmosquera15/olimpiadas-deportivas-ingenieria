import { create } from 'zustand';
import { AuthDTO, Usuario } from '@/types';
import { getToken, clearToken, setToken as saveToken, parseJWT, isTokenExpired } from '@/lib/auth';
import { authService } from '@/services/auth.service';

interface AuthState {
  user: Partial<Usuario> | null;
  token: string | null;
  isAuthenticated: boolean;
  profileComplete: boolean;
  authReady: boolean;
  setAuth: (token: string | null, user: Partial<Usuario> | null, profileComplete?: boolean) => void;
  clearAuth: () => void;
  initAuth: () => void;
}

export const useAuth = create<AuthState>((set, get) => ({
  user: null,
  token: null,
  isAuthenticated: false,
  profileComplete: false,
  authReady: false,

  setAuth: (token, user, profileComplete) => {
    if (token) saveToken(token);
    set({
      user: user ?? null,
      token: token ?? null,
      isAuthenticated: !!token,
      profileComplete: profileComplete ?? get().profileComplete,
      authReady: true,
    });
  },

  clearAuth: () => {
    clearToken();
    set({ user: null, token: null, isAuthenticated: false, profileComplete: false, authReady: true });
  },

  initAuth: () => {
    const token = getToken();
    if (!token) {
      set({ authReady: true });
      return;
    }

    const payload = parseJWT<{ exp?: number }>(token);
    const exp = payload?.exp;
    if (!exp || Date.now() >= exp * 1000) {
      clearToken();
      set({ authReady: true });
      return;
    }

    set({ token, isAuthenticated: true, authReady: false });

    authService.getMe()
      .then((me: AuthDTO) => {
        set({
          user: {
            id: me.id ?? undefined,
            nombre: me.nombre ?? null,
            correo: me.correo ?? null,
            fotoUrl: me.fotoUrl ?? null,
          },
          profileComplete: !!me.completo,
          authReady: true,
        });
      })
      .catch(() => {
        set({ authReady: true });
      });
  },
}));
