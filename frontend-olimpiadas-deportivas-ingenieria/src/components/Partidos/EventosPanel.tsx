import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { eventosService } from '@/services/eventos.service';
import { catalogoService } from '@/services/catalogo.service';
import { equiposService } from '@/services/equipos.service';
import { Partido } from '@/types';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Textarea } from '@/components/ui/textarea';
import { Separator } from '@/components/ui/separator';
import { Badge } from '@/components/ui/badge';
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from '@/components/ui/alert-dialog';
import { useToast } from '@/hooks/use-toast';
import { Loader2, Trash2, Plus } from 'lucide-react';

interface EventosPanelProps {
  partido: Partido;
}

export function EventosPanel({ partido }: EventosPanelProps) {
  const [tipoEventoId, setTipoEventoId] = useState<string>('');
  const [usuarioId, setUsuarioId] = useState<string>('');
  const [minuto, setMinuto] = useState<string>('');
  const [observaciones, setObservaciones] = useState<string>('');
  const [equipoSeleccionado, setEquipoSeleccionado] = useState<'1' | '2'>('1');

  const { toast } = useToast();
  const queryClient = useQueryClient();

  const { data: eventos, isLoading: loadingEventos } = useQuery({
    queryKey: ['eventos', partido.id],
    queryFn: () => eventosService.getEventos(partido.id),
  });

  const { data: tiposEvento } = useQuery({
    queryKey: ['tipos-evento', partido.torneo.deporte.id],
    queryFn: () => catalogoService.getTiposEvento(partido.torneo.deporte.id),
  });

  const equipoIdActual = equipoSeleccionado === '1' ? partido.equipo1?.id : partido.equipo2?.id;

  const { data: plantilla } = useQuery({
    queryKey: ['plantilla', equipoIdActual, partido.torneoId],
    queryFn: () => equiposService.getPlantilla(equipoIdActual!, partido.torneoId),
    enabled: !!equipoIdActual,
  });

  const createMutation = useMutation({
    mutationFn: eventosService.createEvento,
    onSuccess: () => {
      toast({
        title: 'Evento registrado',
        description: 'El evento se ha registrado correctamente',
      });
      queryClient.invalidateQueries({ queryKey: ['eventos', partido.id] });
      setTipoEventoId('');
      setUsuarioId('');
      setMinuto('');
      setObservaciones('');
    },
    onError: (error: any) => {
      toast({
        variant: 'destructive',
        title: 'Error al registrar evento',
        description: error.response?.data?.message || 'Ocurrió un error al registrar el evento',
      });
    },
  });

  const deleteMutation = useMutation({
    mutationFn: eventosService.deleteEvento,
    onSuccess: () => {
      toast({
        title: 'Evento eliminado',
        description: 'El evento se ha eliminado correctamente',
      });
      queryClient.invalidateQueries({ queryKey: ['eventos', partido.id] });
    },
    onError: (error: any) => {
      toast({
        variant: 'destructive',
        title: 'Error al eliminar evento',
        description: error.response?.data?.message || 'Ocurrió un error al eliminar el evento',
      });
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!tipoEventoId || !usuarioId) {
      toast({
        variant: 'destructive',
        title: 'Campos incompletos',
        description: 'Debes seleccionar el tipo de evento y el jugador',
      });
      return;
    }

    createMutation.mutate({
      partidoId: partido.id,
      tipoEventoId: Number(tipoEventoId),
      usuarioId: Number(usuarioId),
      minuto: minuto ? Number(minuto) : undefined,
      observaciones: observaciones || undefined,
    });
  };

  if (loadingEventos) {
    return (
      <div className="flex justify-center py-8">
        <Loader2 className="h-6 w-6 animate-spin text-primary" />
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Lista de eventos */}
      <div className="space-y-3">
        {eventos && eventos.length > 0 ? (
          eventos.map((evento) => (
            <div
              key={evento.id}
              className="flex items-start justify-between p-3 border rounded-lg"
            >
              <div className="flex-1 space-y-1">
                <div className="flex items-center gap-2">
                  <Badge variant="outline">{evento.tipoEvento.nombre}</Badge>
                  {evento.minuto && (
                    <span className="text-sm text-muted-foreground">{evento.minuto}'</span>
                  )}
                  <span className="text-sm font-medium">{evento.usuario.nombre}</span>
                </div>
                {evento.observaciones && (
                  <p className="text-sm text-muted-foreground">{evento.observaciones}</p>
                )}
                <p className="text-xs text-muted-foreground">
                  Puntos negativos: {evento.tipoEvento.puntosNegativos}
                </p>
              </div>
              <AlertDialog>
                <AlertDialogTrigger asChild>
                  <Button variant="ghost" size="icon">
                    <Trash2 className="h-4 w-4 text-destructive" />
                  </Button>
                </AlertDialogTrigger>
                <AlertDialogContent>
                  <AlertDialogHeader>
                    <AlertDialogTitle>¿Eliminar evento?</AlertDialogTitle>
                    <AlertDialogDescription>
                      Esta acción no se puede deshacer. El evento será eliminado permanentemente.
                    </AlertDialogDescription>
                  </AlertDialogHeader>
                  <AlertDialogFooter>
                    <AlertDialogCancel>Cancelar</AlertDialogCancel>
                    <AlertDialogAction onClick={() => deleteMutation.mutate(evento.id)}>
                      Eliminar
                    </AlertDialogAction>
                  </AlertDialogFooter>
                </AlertDialogContent>
              </AlertDialog>
            </div>
          ))
        ) : (
          <p className="text-center text-muted-foreground py-8">
            No hay eventos registrados para este partido
          </p>
        )}
      </div>

      <Separator />

      {/* Formulario para crear evento */}
      <div>
        <h3 className="text-lg font-semibold mb-4">Registrar Evento</h3>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="grid gap-4 md:grid-cols-2">
            <div className="space-y-2">
              <label className="text-sm font-medium">Equipo</label>
              <Select value={equipoSeleccionado} onValueChange={(v) => {
                setEquipoSeleccionado(v as '1' | '2');
                setUsuarioId('');
              }}>
                <SelectTrigger>
                  <SelectValue />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="1">{partido.equipo1?.nombre}</SelectItem>
                  <SelectItem value="2">{partido.equipo2?.nombre}</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium">Tipo de Evento</label>
              <Select value={tipoEventoId} onValueChange={setTipoEventoId}>
                <SelectTrigger>
                  <SelectValue placeholder="Seleccionar evento" />
                </SelectTrigger>
                <SelectContent>
                  {tiposEvento?.map((tipo) => (
                    <SelectItem key={tipo.id} value={tipo.id.toString()}>
                      {tipo.nombre} ({tipo.puntosNegativos} pts. neg.)
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium">Jugador</label>
              <Select value={usuarioId} onValueChange={setUsuarioId}>
                <SelectTrigger>
                  <SelectValue placeholder="Seleccionar jugador" />
                </SelectTrigger>
                <SelectContent>
                  {plantilla?.map((p) => (
                    <SelectItem key={p.usuarioId} value={p.usuarioId.toString()}>
                      {p.usuario.nombre}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium">Minuto (opcional)</label>
              <Input
                type="number"
                min="0"
                value={minuto}
                onChange={(e) => setMinuto(e.target.value)}
                placeholder="0"
              />
            </div>
          </div>

          <div className="space-y-2">
            <label className="text-sm font-medium">Observaciones (opcional)</label>
            <Textarea
              value={observaciones}
              onChange={(e) => setObservaciones(e.target.value)}
              placeholder="Observaciones adicionales"
              rows={3}
            />
          </div>

          <Button type="submit" disabled={createMutation.isPending}>
            {createMutation.isPending ? (
              <Loader2 className="h-4 w-4 animate-spin mr-2" />
            ) : (
              <Plus className="h-4 w-4 mr-2" />
            )}
            Registrar Evento
          </Button>
        </form>
      </div>
    </div>
  );
}
