import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { torneosService } from '@/services/torneos.service';
import { olimpiadasService } from '@/services/olimpiadas.service';
import { useCatalogos } from '@/hooks/useCatalogos';
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { toast } from 'sonner';
import { Plus, Loader2 } from 'lucide-react';
import axios from 'axios';
import type { TorneoCreateRequest } from '@/types';

export function CrearTorneoForm() {
  const [open, setOpen] = useState(false);
  const [nombre, setNombre] = useState('');
  const [idDeporte, setIdDeporte] = useState<number | undefined>(undefined);
  const [idOlimpiada, setIdOlimpiada] = useState<number | undefined>(undefined);
  
  const queryClient = useQueryClient();
  const { deportes } = useCatalogos();
  
  const { data: olimpiadas, isLoading: loadingOlimpiadas } = useQuery({
    queryKey: ['olimpiadas'],
    queryFn: () => olimpiadasService.getOlimpiadas(),
  });

  const createMutation = useMutation({
    mutationFn: (request: TorneoCreateRequest) => torneosService.createTorneo(request),
    onSuccess: () => {
      toast.success('Torneo creado exitosamente');
      queryClient.invalidateQueries({ queryKey: ['torneos'] });
      setOpen(false);
      resetForm();
    },
    onError: (error: unknown) => {
      let message = 'Error al crear torneo';
      if (axios.isAxiosError(error)) {
        message = error.response?.data?.message || error.message || message;
      } else if (error instanceof Error) {
        message = error.message;
      }
      toast.error(message);
    },
  });

  const resetForm = () => {
    setNombre('');
    setIdDeporte(undefined);
    setIdOlimpiada(undefined);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!nombre.trim()) {
      toast.error('El nombre es requerido');
      return;
    }
    
    if (!idDeporte) {
      toast.error('Debe seleccionar un deporte');
      return;
    }
    
    if (!idOlimpiada) {
      toast.error('Debe seleccionar una olimpiada');
      return;
    }

    createMutation.mutate({
      nombre: nombre.trim(),
      idDeporte,
      idOlimpiada,
    });
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button>
          <Plus className="mr-2 h-4 w-4" />
          Crear Torneo
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[500px]">
        <DialogHeader>
          <DialogTitle>Crear Nuevo Torneo</DialogTitle>
          <DialogDescription>
            Completa los datos para crear un nuevo torneo deportivo
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="nombre">Nombre del Torneo</Label>
            <Input
              id="nombre"
              value={nombre}
              onChange={(e) => setNombre(e.target.value)}
              placeholder="Ej: Fútbol Masculino"
              disabled={createMutation.isPending}
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="olimpiada">Olimpiada</Label>
            <Select
              value={idOlimpiada?.toString()}
              onValueChange={(value) => setIdOlimpiada(Number(value))}
              disabled={loadingOlimpiadas || createMutation.isPending}
            >
              <SelectTrigger id="olimpiada">
                <SelectValue placeholder="Selecciona una olimpiada" />
              </SelectTrigger>
              <SelectContent>
                {olimpiadas?.map((olimpiada) => (
                  <SelectItem key={olimpiada.id} value={olimpiada.id.toString()}>
                    {olimpiada.nombre}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div className="space-y-2">
            <Label htmlFor="deporte">Deporte</Label>
            <Select
              value={idDeporte?.toString()}
              onValueChange={(value) => setIdDeporte(Number(value))}
              disabled={createMutation.isPending}
            >
              <SelectTrigger id="deporte">
                <SelectValue placeholder="Selecciona un deporte" />
              </SelectTrigger>
              <SelectContent>
                {deportes.data?.map((deporte) => (
                  <SelectItem key={deporte.id} value={deporte.id.toString()}>
                    {deporte.nombre}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div className="flex justify-end gap-3 pt-4">
            <Button
              type="button"
              variant="outline"
              onClick={() => setOpen(false)}
              disabled={createMutation.isPending}
            >
              Cancelar
            </Button>
            <Button type="submit" disabled={createMutation.isPending}>
              {createMutation.isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
              Crear Torneo
            </Button>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
}
