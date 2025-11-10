import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { AppLayout } from '@/components/Layout/AppLayout';
import { torneosService } from '@/services/torneos.service';
import { useCatalogos } from '@/hooks/useCatalogos';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Link } from 'react-router-dom';
import { Trophy, Search, Filter } from 'lucide-react';
import { Loader2 } from 'lucide-react';

export default function ListadoTorneos() {
  const [deporteId, setDeporteId] = useState<number | undefined>();
  const [anio, setAnio] = useState<number | undefined>();
  const [activo, setActivo] = useState<boolean | undefined>();
  const [page, setPage] = useState(0);

  const { deportes } = useCatalogos();

  const { data, isLoading } = useQuery({
    queryKey: ['torneos', { deporteId, anio, activo, page }],
    queryFn: () => torneosService.getTorneos({ deporteId, anio, activo, page, size: 12 }),
  });

  const currentYear = new Date().getFullYear();
  const years = Array.from({ length: 10 }, (_, i) => currentYear - i);

  return (
    <AppLayout>
      <div className="space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold tracking-tight">Torneos</h1>
            <p className="text-muted-foreground">Consulta y gestiona los torneos deportivos</p>
          </div>
        </div>

        {/* Filters */}
        <Card>
          <CardHeader>
            <CardTitle className="text-lg flex items-center gap-2">
              <Filter className="h-5 w-5" />
              Filtros
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid gap-4 md:grid-cols-3">
              <div>
                <label className="text-sm font-medium mb-2 block">Deporte</label>
                <Select
                  value={deporteId?.toString()}
                  onValueChange={(value) => setDeporteId(value ? Number(value) : undefined)}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Todos los deportes" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="">Todos los deportes</SelectItem>
                    {deportes.data?.map((deporte) => (
                      <SelectItem key={deporte.id} value={deporte.id.toString()}>
                        {deporte.nombre}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>

              <div>
                <label className="text-sm font-medium mb-2 block">Año</label>
                <Select
                  value={anio?.toString()}
                  onValueChange={(value) => setAnio(value ? Number(value) : undefined)}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Todos los años" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="">Todos los años</SelectItem>
                    {years.map((year) => (
                      <SelectItem key={year} value={year.toString()}>
                        {year}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>

              <div>
                <label className="text-sm font-medium mb-2 block">Estado</label>
                <Select
                  value={activo === undefined ? '' : activo.toString()}
                  onValueChange={(value) =>
                    setActivo(value === '' ? undefined : value === 'true')
                  }
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Todos" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="">Todos</SelectItem>
                    <SelectItem value="true">Activos</SelectItem>
                    <SelectItem value="false">Inactivos</SelectItem>
                  </SelectContent>
                </Select>
              </div>
            </div>
          </CardContent>
        </Card>

        {/* Torneos Grid */}
        {isLoading ? (
          <div className="flex justify-center py-12">
            <Loader2 className="h-8 w-8 animate-spin text-primary" />
          </div>
        ) : (
          <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
            {data?.content.map((torneo) => (
              <Link key={torneo.id} to={`/torneos/${torneo.id}`}>
                <Card className="h-full transition-all hover:shadow-md">
                  <CardHeader>
                    <div className="flex items-start justify-between">
                      <Trophy className="h-8 w-8 text-primary" />
                      {torneo.activo ? (
                        <Badge className="bg-success">Activo</Badge>
                      ) : (
                        <Badge variant="secondary">Inactivo</Badge>
                      )}
                    </div>
                    <CardTitle className="line-clamp-2">{torneo.nombre}</CardTitle>
                    <CardDescription>
                      {torneo.deporte.nombre} • {torneo.anio}
                    </CardDescription>
                  </CardHeader>
                  <CardContent>
                    {torneo.reglamentoUrl && (
                      <Button variant="outline" size="sm" className="w-full" asChild>
                        <a
                          href={torneo.reglamentoUrl}
                          target="_blank"
                          rel="noopener noreferrer"
                          onClick={(e) => e.stopPropagation()}
                        >
                          Ver Reglamento
                        </a>
                      </Button>
                    )}
                  </CardContent>
                </Card>
              </Link>
            ))}
          </div>
        )}

        {data && data.content.length === 0 && (
          <Card>
            <CardContent className="py-12 text-center">
              <Trophy className="mx-auto h-12 w-12 text-muted-foreground" />
              <p className="mt-4 text-muted-foreground">No se encontraron torneos</p>
            </CardContent>
          </Card>
        )}

        {/* Pagination */}
        {data && data.totalPages > 1 && (
          <div className="flex justify-center gap-2">
            <Button
              variant="outline"
              onClick={() => setPage((p) => Math.max(0, p - 1))}
              disabled={page === 0}
            >
              Anterior
            </Button>
            <span className="flex items-center px-4">
              Página {page + 1} de {data.totalPages}
            </span>
            <Button
              variant="outline"
              onClick={() => setPage((p) => p + 1)}
              disabled={page >= data.totalPages - 1}
            >
              Siguiente
            </Button>
          </div>
        )}
      </div>
    </AppLayout>
  );
}
