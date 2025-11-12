import { TeamStats } from '@/types/equipo';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';

interface EstadisticasEquipoProps {
  stats: TeamStats;
}

export function EstadisticasEquipo({ stats }: EstadisticasEquipoProps) {
  return (
    <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
      <Card>
        <CardHeader>
          <CardTitle>Partidos</CardTitle>
          <CardDescription>Resumen de partidos</CardDescription>
        </CardHeader>
        <CardContent>
          <dl className="space-y-2">
            <div className="flex justify-between">
              <dt>Jugados</dt>
              <dd>{stats.partidosJugados}</dd>
            </div>
            <div className="flex justify-between">
              <dt>Ganados</dt>
              <dd>{stats.partidosGanados}</dd>
            </div>
            <div className="flex justify-between">
              <dt>Empatados</dt>
              <dd>{stats.partidosEmpatados}</dd>
            </div>
            <div className="flex justify-between">
              <dt>Perdidos</dt>
              <dd>{stats.partidosPerdidos}</dd>
            </div>
          </dl>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Goles</CardTitle>
          <CardDescription>Estadísticas de goles</CardDescription>
        </CardHeader>
        <CardContent>
          <dl className="space-y-2">
            <div className="flex justify-between">
              <dt>A favor</dt>
              <dd>{stats.golesFavor}</dd>
            </div>
            <div className="flex justify-between">
              <dt>En contra</dt>
              <dd>{stats.golesContra}</dd>
            </div>
            <div className="flex justify-between">
              <dt>Diferencia</dt>
              <dd>{stats.diferenciaGoles}</dd>
            </div>
          </dl>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Fair Play</CardTitle>
          <CardDescription>Puntuación de juego limpio</CardDescription>
        </CardHeader>
        <CardContent>
          <dl className="space-y-2">
            <div className="flex justify-between">
              <dt>Puntos</dt>
              <dd>{stats.puntos}</dd>
            </div>
            <div className="flex justify-between">
              <dt>Fair Play</dt>
              <dd>{stats.fairPlayPoints.toFixed(2)}</dd>
            </div>
          </dl>
        </CardContent>
      </Card>
    </div>
  );
}