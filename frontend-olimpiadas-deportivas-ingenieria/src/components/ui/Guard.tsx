import { ReactNode } from 'react';
import { hasPermission, getUserRole } from '@/lib/auth';
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip';

interface GuardProps {
  children: ReactNode;
  permiso?: string;
  rol?: string;
  fallback?: ReactNode;
  tooltipMessage?: string;
}

export const Guard = ({ children, permiso, rol, fallback, tooltipMessage }: GuardProps) => {
  let hasAccess = true;

  if (permiso) {
    hasAccess = hasPermission(permiso);
  }

  if (rol && hasAccess) {
    const userRole = getUserRole();
    hasAccess = userRole === rol;
  }

  if (!hasAccess) {
    if (tooltipMessage) {
      return (
        <TooltipProvider>
          <Tooltip>
            <TooltipTrigger asChild>
              <div className="cursor-not-allowed opacity-50">{fallback || children}</div>
            </TooltipTrigger>
            <TooltipContent>
              <p>{tooltipMessage}</p>
            </TooltipContent>
          </Tooltip>
        </TooltipProvider>
      );
    }
    return fallback ? <>{fallback}</> : null;
  }

  return <>{children}</>;
};
