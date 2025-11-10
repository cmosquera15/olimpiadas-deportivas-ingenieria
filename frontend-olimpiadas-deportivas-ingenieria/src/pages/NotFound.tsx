import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { FileQuestion } from 'lucide-react';

export default function NotFound() {
  return (
    <div className="flex min-h-screen flex-col items-center justify-center bg-background p-4">
      <div className="text-center">
        <FileQuestion className="mx-auto h-24 w-24 text-muted-foreground" />
        <h1 className="mt-4 text-4xl font-bold">404</h1>
        <h2 className="mt-2 text-2xl font-semibold">Página No Encontrada</h2>
        <p className="mt-4 text-muted-foreground">
          La página que buscas no existe o ha sido movida.
        </p>
        <Button asChild className="mt-6">
          <Link to="/dashboard">Volver al Inicio</Link>
        </Button>
      </div>
    </div>
  );
}
