const STORAGE_KEY = import.meta.env.VITE_JWT_STORAGE_KEY || 'olimpiadas_jwt';

export interface JWTPayload {
  sub: string;
  rol: string;
  permisos: string[];
  exp: number;
  iat: number;
}

export const getToken = (): string | null => {
  return localStorage.getItem(STORAGE_KEY);
};

export const setToken = (token: string): void => {
  localStorage.setItem(STORAGE_KEY, token);
};

export const clearToken = (): void => {
  localStorage.removeItem(STORAGE_KEY);
};

export const parseJWT = (token: string): JWTPayload | null => {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload);
  } catch (error) {
    console.error('Error parsing JWT:', error);
    return null;
  }
};

export const isTokenExpired = (token: string): boolean => {
  const payload = parseJWT(token);
  if (!payload) return true;
  return Date.now() >= payload.exp * 1000;
};

export const hasPermission = (permission: string): boolean => {
  const token = getToken();
  if (!token) return false;
  
  const payload = parseJWT(token);
  if (!payload) return false;
  
  return payload.permisos?.includes(permission) || false;
};

export const getUserRole = (): string | null => {
  const token = getToken();
  if (!token) return null;
  
  const payload = parseJWT(token);
  return payload?.rol || null;
};
