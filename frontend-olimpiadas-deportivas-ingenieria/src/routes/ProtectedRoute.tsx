import { useEffect } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAuth } from '@/store/useAuth';
import { hasPermission, getUserRole, getToken } from '@/lib/auth';
import { Loader2 } from 'lucide-react';

interface ProtectedRouteProps {
  children: React.ReactElement;
  requiredPermission?: string;
  requiredRole?: 'Administrador' | 'Árbitro' | 'Jugador';
}

export const ProtectedRoute = ({ children, requiredPermission, requiredRole }: ProtectedRouteProps) => {
  const { isAuthenticated, initAuth, profileComplete, authReady } = useAuth(); // ← authReady
  const token = getToken();
  const location = useLocation();

  useEffect(() => {
    initAuth();
  }, [initAuth]);

  // Wait for auth initialization to complete before making routing decisions
  if (!authReady) {
    return (
      <div className="flex h-full w-full items-center justify-center p-8">
        <Loader2 className="mr-2 h-5 w-5 animate-spin" />
        <span>Cargando sesión…</span>
      </div>
    );
  }

  // If not authenticated after init, go to login
  if (!isAuthenticated) return <Navigate to="/auth/login" replace />;

  // Only redirect to complete profile if we're sure the profile is incomplete
  // (authReady ensures we've fetched user data)
  const isCompletarPerfil = location.pathname.startsWith('/auth/completar-perfil');
  if (!profileComplete && !isCompletarPerfil) {
    return <Navigate to="/auth/completar-perfil" replace />;
  }

  if (requiredPermission && !hasPermission(requiredPermission)) {
    return <Navigate to="/forbidden" replace />;
  }

  if (requiredRole) {
    const userRole = getUserRole();
    if (userRole !== requiredRole && userRole !== 'Administrador') {
      return <Navigate to="/forbidden" replace />;
    }
  }

  return <>{children}</>;
};
