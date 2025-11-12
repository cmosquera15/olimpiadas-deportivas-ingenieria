import { useQuery } from '@tanstack/react-query';
import axiosInstance from '@/lib/axios';

/**
 * Hook to check if an endpoint is reachable. It treats any HTTP response
 * (including 4xx/5xx) as "reachable". Network errors/timeouts are treated
 * as unreachable.
 */
export function useEndpointHealth(path: string, options?: { enabled?: boolean }) {
  type HealthResp = { available: boolean; status: number | null };

  const query = useQuery<HealthResp, unknown>({
    queryKey: ['endpoint-health', path],
    queryFn: async () => {
      try {
        const resp = await axiosInstance.get(path, { timeout: 3000, validateStatus: () => true });
        return { available: true, status: resp.status };
      } catch (err) {
        return { available: false, status: null };
      }
    },
    staleTime: 1000 * 60,
    enabled: options?.enabled ?? true,
  });

  return {
    available: query.data?.available ?? false,
    status: query.data?.status ?? null,
    isLoading: query.isLoading,
    isFetching: query.isFetching,
  };
}
