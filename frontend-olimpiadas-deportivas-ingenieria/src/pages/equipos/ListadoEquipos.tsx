import { useQuery } from '@tanstack/react-query';
import { AppLayout } from '@/components/Layout/AppLayout';
import { equiposService } from '@/services/equipos.service';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Link } from 'react-router-dom';
import { Users, Loader2, Shield } from 'lucide-react';

export default function ListadoEquipos() {
  const { data: equipos, isLoading } = useQuery({
    queryKey: ['equipos'],
    queryFn: () => equiposService.getEquipos(),
  });

  return (
    <AppLayout>
      <div className="space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold tracking-tight">Equipos</h1>
            <p className="text-muted-foreground">Consulta y gestiona los equipos</p>
          </div>
        </div>

        {isLoading ? (
          <div className="flex justify-center py-12">
            <Loader2 className="h-8 w-8 animate-spin text-primary" />
          </div>
        ) : (
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            {equipos?.map((equipo) => (
              <Link key={equipo.id} to={`/equipos/${equipo.id}`}>
                <Card className="h-full transition-all hover:shadow-md">
                  <CardHeader>
                    <div className="flex items-start justify-between">
                      <Shield className="h-8 w-8 text-primary" />
                    </div>
                    <CardTitle className="line-clamp-1">{equipo.nombre}</CardTitle>
                    <CardDescription>
                      {equipo.torneo ? equipo.torneo.nombre : 'Sin torneo'}
                    </CardDescription>
                  </CardHeader>
                  <CardContent>
                    <Button variant="outline" size="sm" className="w-full">
                      Ver Detalles
                    </Button>
                  </CardContent>
                </Card>
              </Link>
            ))}
          </div>
        )}

        {equipos && equipos.length === 0 && (
          <Card>
            <CardContent className="py-12 text-center">
              <Users className="mx-auto h-12 w-12 text-muted-foreground" />
              <p className="mt-4 text-muted-foreground">No se encontraron equipos</p>
            </CardContent>
          </Card>
        )}
      </div>
    </AppLayout>
  );
}
