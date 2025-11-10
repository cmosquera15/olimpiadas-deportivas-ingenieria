import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { ShieldAlert } from 'lucide-react';

export default function Forbidden() {
  return (
    <div className="flex min-h-screen flex-col items-center justify-center bg-background p-4">
      <div className="text-center">
        <ShieldAlert className="mx-auto h-24 w-24 text-destructive" />
        <h1 className="mt-4 text-4xl font-bold">403</h1>
        <h2 className="mt-2 text-2xl font-semibold">Acceso Denegado</h2>
        <p className="mt-4 text-muted-foreground">
          No tienes permisos para acceder a esta página.
        </p>
        <Button asChild className="mt-6">
          <Link to="/dashboard">Volver al Inicio</Link>
        </Button>
      </div>
    </div>
  );
}
