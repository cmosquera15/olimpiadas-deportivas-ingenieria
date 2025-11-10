import { ReactNode, useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '@/store/useAuth';
import { hasPermission, getUserRole } from '@/lib/auth';
import { Loader2 } from 'lucide-react';

interface ProtectedRouteProps {
  children: ReactNode;
  requiredPermission?: string;
  requiredRole?: string;
}

export const ProtectedRoute = ({
  children,
  requiredPermission,
  requiredRole,
}: ProtectedRouteProps) => {
  const { isAuthenticated, initAuth } = useAuth();

  useEffect(() => {
    initAuth();
  }, [initAuth]);

  if (!isAuthenticated) {
    return <Navigate to="/auth/login" replace />;
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
