import { useQuery } from '@tanstack/react-query';
import { catalogoService } from '@/services/catalogo.service';

export const useCatalogos = () => {
  const programas = useQuery({
    queryKey: ['programas'],
    queryFn: catalogoService.getProgramas,
    staleTime: Infinity,
  });

  const eps = useQuery({
    queryKey: ['eps'],
    queryFn: catalogoService.getEPS,
    staleTime: Infinity,
  });

  const generos = useQuery({
    queryKey: ['generos'],
    queryFn: catalogoService.getGeneros,
    staleTime: Infinity,
  });

  const deportes = useQuery({
    queryKey: ['deportes'],
    queryFn: catalogoService.getDeportes,
    staleTime: Infinity,
  });

  const lugares = useQuery({
    queryKey: ['lugares'],
    queryFn: catalogoService.getLugares,
    staleTime: Infinity,
  });

  const fases = useQuery({
    queryKey: ['fases'],
    queryFn: catalogoService.getFases,
    staleTime: Infinity,
  });

  const resultados = useQuery({
    queryKey: ['resultados'],
    queryFn: catalogoService.getResultados,
    staleTime: Infinity,
  });

  const tiposVinculo = useQuery({
    queryKey: ['tipos-vinculo'],
    queryFn: catalogoService.getTiposVinculo,
    staleTime: Infinity,
  });

  return {
    programas,
    eps,
    generos,
    deportes,
    lugares,
    fases,
    resultados,
    tiposVinculo,
  };
};

export const useJornadas = (torneoId: number | undefined) => {
  return useQuery({
    queryKey: ['jornadas', torneoId],
    queryFn: () => catalogoService.getJornadas(torneoId!),
    enabled: !!torneoId,
  });
};

export const useGrupos = (torneoId: number | undefined) => {
  return useQuery({
    queryKey: ['grupos', torneoId],
    queryFn: () => catalogoService.getGrupos(torneoId!),
    enabled: !!torneoId,
  });
};

export const useTiposEvento = (deporteId?: number) => {
  return useQuery({
    queryKey: ['tipos-evento', deporteId],
    queryFn: () => catalogoService.getTiposEvento(deporteId),
  });
};
