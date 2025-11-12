const STORAGE_KEY = import.meta.env.VITE_JWT_STORAGE_KEY || 'olimpiadas_jwt';

type AnyRecord = Record<string, unknown>;

export interface JWTPayload {
  sub: string;
  rol: string;
  permisos: string[];
  exp: number;
  iat: number;
  auth?: string | string[];
  authorities?: string | string[];
  roles?: string | string[];
}

export const getToken = (): string | null => {
  const raw = localStorage.getItem(STORAGE_KEY);
  if (!raw) return null;
  return raw.replace(/^Bearer\s+/i, '').trim();
};

export const setToken = (token: string): void => {
  const clean = (token || '').replace(/^Bearer\s+/i, '').trim();
  localStorage.setItem(STORAGE_KEY, clean);
};

export const clearToken = (): void => {
  localStorage.removeItem(STORAGE_KEY);
};

export const parseJWT = <T = AnyRecord>(token?: string | null): T | null => {
  try {
    if (!token) return null;
    const clean = token.replace(/^Bearer\s+/i, '').trim();
    const parts = clean.split('.');
    if (parts.length < 3) return null;

    const base64Url = parts[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const json = atob(base64);
    const uriDecoded = decodeURIComponent(
      Array.from(json)
        .map((c) => '%' + c.charCodeAt(0).toString(16).padStart(2, '0'))
        .join('')
    );
    return JSON.parse(uriDecoded) as T;
  } catch (e) {
    console.error('Error parsing JWT:', e);
    return null;
  }
};

export const isTokenExpired = (token: string | null): boolean => {
  const payload = parseJWT<JWTPayload>(token);
  const exp = payload?.exp;
  if (!exp) return true;
  return Date.now() >= exp * 1000;
};

export const getAuthorities = (): string[] => {
  const payload = parseJWT<JWTPayload>(getToken());
  if (!payload) return [];

  const raw =
    payload.auth ?? payload.authorities ?? payload.roles ?? payload.permisos ?? [];

  if (Array.isArray(raw)) {
    return raw.map((s) => String(s).trim()).filter(Boolean);
  }
  if (typeof raw === 'string') {
    return raw
      .split(',')
      .map((s) => s.trim())
      .filter(Boolean);
  }
  return [];
};

export const hasPermission = (permission: string): boolean => {
  const authz = getAuthorities();
  return authz.includes(permission);
};

export const getUserRole = (): string | null => {
  const authz = getAuthorities();
  const roleAuth = authz.find((a) => /^ROLE_/i.test(a)) || '';
  if (/^ROLE_ADMINISTRADOR$/i.test(roleAuth)) return 'Administrador';
  if (/^ROLE_ARBITRO$/i.test(roleAuth)) return 'Árbitro';
  if (roleAuth) return 'Jugador';
  return null;
};