import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { AppLayout } from '@/components/Layout/AppLayout';
import { posicionesService } from '@/services/posiciones.service';
import { torneosService } from '@/services/torneos.service';
import { useGrupos } from '@/hooks/useCatalogos';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert';
import { Loader2, Trophy, Info } from 'lucide-react';

export default function TablaPosiciones() {
  const [torneoId, setTorneoId] = useState<number | undefined>();
  const [grupoId, setGrupoId] = useState<number | undefined>();

  const torneosQuery = useQuery({
    queryKey: ['torneos-activos'],
    queryFn: () => torneosService.getTorneos({ activo: true, size: 100 }),
  });

  const { data: grupos } = useGrupos(torneoId);

  const { data: posiciones, isLoading } = useQuery({
    queryKey: ['posiciones', torneoId, grupoId],
    queryFn: () => posicionesService.getPosiciones(torneoId!, grupoId),
    enabled: !!torneoId,
  });

  return (
    <AppLayout>
      <div className="space-y-6">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Tabla de Posiciones</h1>
          <p className="text-muted-foreground">Consulta las posiciones y estadísticas</p>
        </div>

        {/* Filters */}
        <Card>
          <CardHeader>
            <CardTitle className="text-lg">Selecciona un Torneo</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid gap-4 md:grid-cols-2">
              <div>
                <label className="text-sm font-medium mb-2 block">Torneo</label>
                <Select
                  value={torneoId?.toString()}
                  onValueChange={(value) => {
                    setTorneoId(value ? Number(value) : undefined);
                    setGrupoId(undefined);
                  }}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Selecciona un torneo" />
                  </SelectTrigger>
                  <SelectContent>
                    {torneosQuery.data?.content.map((torneo) => (
                      <SelectItem key={torneo.id} value={torneo.id.toString()}>
                        {torneo.nombre} ({torneo.deporte.nombre})
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>

              {grupos && grupos.length > 0 && (
                <div>
                  <label className="text-sm font-medium mb-2 block">Grupo (Opcional)</label>
                  <Select
                    value={grupoId?.toString()}
                    onValueChange={(value) => setGrupoId(value ? Number(value) : undefined)}
                  >
                    <SelectTrigger>
                      <SelectValue placeholder="Todos los grupos" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="">Todos los grupos</SelectItem>
                      {grupos.map((grupo) => (
                        <SelectItem key={grupo.id} value={grupo.id.toString()}>
                          {grupo.nombre}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
              )}
            </div>
          </CardContent>
        </Card>

        {/* Criterios de Desempate */}
        <Alert>
          <Info className="h-4 w-4" />
          <AlertTitle>Criterios de Desempate</AlertTitle>
          <AlertDescription>
            1. Mayor número de puntos
            <br />
            2. Mayor diferencia de goles (GD)
            <br />
            3. Mayor número de goles a favor (GF)
            <br />
            4. Mejor promedio de Fair Play
          </AlertDescription>
        </Alert>

        {/* Tabla de Posiciones */}
        {!torneoId ? (
          <Card>
            <CardContent className="py-12 text-center">
              <Trophy className="mx-auto h-12 w-12 text-muted-foreground" />
              <p className="mt-4 text-muted-foreground">Selecciona un torneo para ver las posiciones</p>
            </CardContent>
          </Card>
        ) : isLoading ? (
          <div className="flex justify-center py-12">
            <Loader2 className="h-8 w-8 animate-spin text-primary" />
          </div>
        ) : posiciones && posiciones.length > 0 ? (
          <Card>
            <CardHeader>
              <CardTitle>Posiciones</CardTitle>
              <CardDescription>PJ=Partidos Jugados, PG=Ganados, PE=Empatados, PP=Perdidos, GF=Goles a Favor, GC=Goles en Contra, GD=Diferencia de Goles, PTS=Puntos</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="overflow-x-auto">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead className="w-12">#</TableHead>
                      <TableHead>Equipo</TableHead>
                      <TableHead className="text-center">PJ</TableHead>
                      <TableHead className="text-center">PG</TableHead>
                      <TableHead className="text-center">PE</TableHead>
                      <TableHead className="text-center">PP</TableHead>
                      <TableHead className="text-center">GF</TableHead>
                      <TableHead className="text-center">GC</TableHead>
                      <TableHead className="text-center">GD</TableHead>
                      <TableHead className="text-center font-bold">PTS</TableHead>
                      <TableHead className="text-center">Fair Play</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {posiciones.map((posicion, index) => (
                      <TableRow key={posicion.equipo.id}>
                        <TableCell className="font-medium">{index + 1}</TableCell>
                        <TableCell className="font-medium">{posicion.equipo.nombre}</TableCell>
                        <TableCell className="text-center">{posicion.pj}</TableCell>
                        <TableCell className="text-center">{posicion.pg}</TableCell>
                        <TableCell className="text-center">{posicion.pe}</TableCell>
                        <TableCell className="text-center">{posicion.pp}</TableCell>
                        <TableCell className="text-center">{posicion.gf}</TableCell>
                        <TableCell className="text-center">{posicion.gc}</TableCell>
                        <TableCell className="text-center">{posicion.gd > 0 ? '+' : ''}{posicion.gd}</TableCell>
                        <TableCell className="text-center font-bold text-primary">{posicion.pts}</TableCell>
                        <TableCell className="text-center">{posicion.fairPlay.toFixed(2)}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </div>
            </CardContent>
          </Card>
        ) : (
          <Card>
            <CardContent className="py-12 text-center">
              <Trophy className="mx-auto h-12 w-12 text-muted-foreground" />
              <p className="mt-4 text-muted-foreground">No hay datos de posiciones disponibles</p>
            </CardContent>
          </Card>
        )}

        {/* Fórmula de Fair Play */}
        {posiciones && posiciones.length > 0 && (
          <Alert>
            <Info className="h-4 w-4" />
            <AlertTitle>Fórmula de Juego Limpio (Fair Play)</AlertTitle>
            <AlertDescription>
              Fair Play = Suma de Puntos Negativos / Partidos Jugados
              <br />
              <span className="text-sm">Un valor menor indica mejor comportamiento deportivo</span>
            </AlertDescription>
          </Alert>
        )}
      </div>
    </AppLayout>
  );
}
