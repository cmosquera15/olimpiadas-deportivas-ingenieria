import { useState, useEffect } from 'react';
import { useMutation, useQuery } from '@tanstack/react-query';
import { partidosService } from '@/services/partidos.service';
import { catalogoService } from '@/services/catalogo.service';
import { Partido } from '@/types';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { useToast } from '@/hooks/use-toast';
import { Loader2 } from 'lucide-react';
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip';

interface MarcadorFormProps {
  partido: Partido;
  onSuccess?: () => void;
}

export function MarcadorForm({ partido, onSuccess }: MarcadorFormProps) {
  const [puntosEquipo1, setPuntosEquipo1] = useState<string>(
    partido.puntosEquipo1?.toString() ?? ''
  );
  const [puntosEquipo2, setPuntosEquipo2] = useState<string>(
    partido.puntosEquipo2?.toString() ?? ''
  );
  const [resultadoEquipo1Id, setResultadoEquipo1Id] = useState<string>(
    partido.resultadoEquipo1Id?.toString() ?? ''
  );
  const [resultadoEquipo2Id, setResultadoEquipo2Id] = useState<string>(
    partido.resultadoEquipo2Id?.toString() ?? ''
  );

  const { toast } = useToast();

  const { data: resultados } = useQuery({
    queryKey: ['resultados'],
    queryFn: catalogoService.getResultados,
  });

  const isBasketball = partido.torneo.deporte.nombre.toLowerCase().includes('baloncesto');

  // Validación: en baloncesto no puede haber empate a menos que haya W.O.
  const hasWO =
    resultados?.some(
      (r) =>
        r.codigo === 'WO' &&
        (r.id.toString() === resultadoEquipo1Id || r.id.toString() === resultadoEquipo2Id)
    ) ?? false;

  const isDraw =
    puntosEquipo1 !== '' &&
    puntosEquipo2 !== '' &&
    Number(puntosEquipo1) === Number(puntosEquipo2);

  const isInvalid = isBasketball && isDraw && !hasWO;

  const mutation = useMutation({
    mutationFn: () =>
      partidosService.actualizarMarcador(partido.id, {
        puntosEquipo1: puntosEquipo1 ? Number(puntosEquipo1) : undefined,
        puntosEquipo2: puntosEquipo2 ? Number(puntosEquipo2) : undefined,
        resultadoEquipo1Id: resultadoEquipo1Id ? Number(resultadoEquipo1Id) : undefined,
        resultadoEquipo2Id: resultadoEquipo2Id ? Number(resultadoEquipo2Id) : undefined,
      }),
    onSuccess: () => {
      toast({
        title: 'Marcador actualizado',
        description: 'El marcador se ha actualizado correctamente',
      });
      onSuccess?.();
    },
    onError: (error: any) => {
      toast({
        variant: 'destructive',
        title: 'Error al actualizar marcador',
        description: error.response?.data?.message || 'Ocurrió un error al actualizar el marcador',
      });
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (isInvalid) {
      return;
    }
    mutation.mutate();
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="grid gap-4 md:grid-cols-2">
        {/* Equipo 1 */}
        <div className="space-y-4">
          <div className="space-y-2">
            <label className="text-sm font-medium">Puntos {partido.equipo1?.nombre}</label>
            <Input
              type="number"
              min="0"
              value={puntosEquipo1}
              onChange={(e) => setPuntosEquipo1(e.target.value)}
              placeholder="0"
            />
          </div>
          <div className="space-y-2">
            <label className="text-sm font-medium">Resultado</label>
            <Select value={resultadoEquipo1Id} onValueChange={setResultadoEquipo1Id}>
              <SelectTrigger>
                <SelectValue placeholder="Sin resultado" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="">Sin resultado</SelectItem>
                {resultados?.map((resultado) => (
                  <SelectItem key={resultado.id} value={resultado.id.toString()}>
                    {resultado.nombre}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        </div>

        {/* Equipo 2 */}
        <div className="space-y-4">
          <div className="space-y-2">
            <label className="text-sm font-medium">Puntos {partido.equipo2?.nombre}</label>
            <Input
              type="number"
              min="0"
              value={puntosEquipo2}
              onChange={(e) => setPuntosEquipo2(e.target.value)}
              placeholder="0"
            />
          </div>
          <div className="space-y-2">
            <label className="text-sm font-medium">Resultado</label>
            <Select value={resultadoEquipo2Id} onValueChange={setResultadoEquipo2Id}>
              <SelectTrigger>
                <SelectValue placeholder="Sin resultado" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="">Sin resultado</SelectItem>
                {resultados?.map((resultado) => (
                  <SelectItem key={resultado.id} value={resultado.id.toString()}>
                    {resultado.nombre}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        </div>
      </div>

      <TooltipProvider>
        <Tooltip open={isInvalid ? undefined : false}>
          <TooltipTrigger asChild>
            <div className="inline-block">
              <Button type="submit" disabled={mutation.isPending || isInvalid}>
                {mutation.isPending && <Loader2 className="h-4 w-4 animate-spin mr-2" />}
                Actualizar Marcador
              </Button>
            </div>
          </TooltipTrigger>
          {isInvalid && (
            <TooltipContent>
              <p>
                En baloncesto no puede haber empate a menos que uno de los equipos tenga resultado
                W.O.
              </p>
            </TooltipContent>
          )}
        </Tooltip>
      </TooltipProvider>
    </form>
  );
}
