import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { AppLayout } from '@/components/Layout/AppLayout';
import { partidosService } from '@/services/partidos.service';
import { useCatalogos } from '@/hooks/useCatalogos';
import { formatDateTime } from '@/lib/date';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Link } from 'react-router-dom';
import { Calendar, MapPin, User, Loader2 } from 'lucide-react';

export default function ListadoPartidos() {
  const [torneoId, setTorneoId] = useState<number | undefined>();
  const [faseId, setFaseId] = useState<number | undefined>();
  const [page, setPage] = useState(0);

  const { fases } = useCatalogos();

  const torneosQuery = useQuery({
    queryKey: ['torneos-simple'],
    queryFn: () => partidosService.getPartidos({ size: 100 }),
  });

  const { data, isLoading } = useQuery({
    queryKey: ['partidos', { torneoId, faseId, page }],
    queryFn: () => partidosService.getPartidos({ torneoId, faseId, page, size: 10 }),
  });

  return (
    <AppLayout>
      <div className="space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold tracking-tight">Partidos</h1>
            <p className="text-muted-foreground">Consulta los partidos programados</p>
          </div>
        </div>

        {/* Filters */}
        <Card>
          <CardHeader>
            <CardTitle className="text-lg">Filtros</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid gap-4 md:grid-cols-2">
              <div>
                <label className="text-sm font-medium mb-2 block">Fase</label>
                <Select
                  value={faseId?.toString()}
                  onValueChange={(value) => setFaseId(value ? Number(value) : undefined)}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Todas las fases" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="">Todas las fases</SelectItem>
                    {fases.data?.map((fase) => (
                      <SelectItem key={fase.id} value={fase.id.toString()}>
                        {fase.nombre}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
            </div>
          </CardContent>
        </Card>

        {/* Partidos List */}
        {isLoading ? (
          <div className="flex justify-center py-12">
            <Loader2 className="h-8 w-8 animate-spin text-primary" />
          </div>
        ) : (
          <div className="space-y-4">
            {data?.content.map((partido) => (
              <Link key={partido.id} to={`/partidos/${partido.id}`}>
                <Card className="transition-all hover:shadow-md">
                  <CardHeader>
                    <div className="flex items-start justify-between">
                      <div>
                        <CardTitle className="text-lg">{partido.torneo.nombre}</CardTitle>
                        <CardDescription>
                          {partido.fase?.nombre}
                          {partido.grupo && ` • ${partido.grupo.nombre}`}
                        </CardDescription>
                      </div>
                      {partido.puntosEquipo1 !== null && partido.puntosEquipo2 !== null && (
                        <Badge>Finalizado</Badge>
                      )}
                    </div>
                  </CardHeader>
                  <CardContent>
                    <div className="grid gap-4 md:grid-cols-3">
                      <div className="flex items-center gap-2 text-sm">
                        <Calendar className="h-4 w-4 text-muted-foreground" />
                        {formatDateTime(new Date(partido.fecha + 'T' + partido.hora))}
                      </div>
                      <div className="flex items-center gap-2 text-sm">
                        <MapPin className="h-4 w-4 text-muted-foreground" />
                        {partido.lugar.nombre}
                      </div>
                      {partido.arbitro && (
                        <div className="flex items-center gap-2 text-sm">
                          <User className="h-4 w-4 text-muted-foreground" />
                          {partido.arbitro.nombre}
                        </div>
                      )}
                    </div>
                    {partido.equipo1 && partido.equipo2 && (
                      <div className="mt-4 flex items-center justify-between border-t pt-4">
                        <div className="flex-1">
                          <p className="font-medium">{partido.equipo1.nombre}</p>
                          {partido.puntosEquipo1 !== null && (
                            <p className="text-2xl font-bold text-primary">
                              {partido.puntosEquipo1}
                            </p>
                          )}
                        </div>
                        <div className="px-4 text-muted-foreground">vs</div>
                        <div className="flex-1 text-right">
                          <p className="font-medium">{partido.equipo2.nombre}</p>
                          {partido.puntosEquipo2 !== null && (
                            <p className="text-2xl font-bold text-primary">
                              {partido.puntosEquipo2}
                            </p>
                          )}
                        </div>
                      </div>
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
              <Calendar className="mx-auto h-12 w-12 text-muted-foreground" />
              <p className="mt-4 text-muted-foreground">No se encontraron partidos</p>
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
