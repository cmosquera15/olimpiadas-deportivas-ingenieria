import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { AppLayout } from '@/components/Layout/AppLayout';
import { usuariosService } from '@/services/usuarios.service';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
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
import { toast } from 'sonner';
import { Search, Loader2, Shield } from 'lucide-react';
import { Usuario } from '@/types';

export default function Usuarios() {
  const [search, setSearch] = useState('');
  const [page, setPage] = useState(0);
  const queryClient = useQueryClient();

  const { data, isLoading, error } = useQuery({
    queryKey: ['usuarios-admin', { search, page }],
    queryFn: () => usuariosService.getUsuarios({ search, page, size: 10 }),
  });

  const updateRolMutation = useMutation({
    mutationFn: ({ id, rol }: { id: number; rol: string }) => usuariosService.updateRol(id, rol),
    onSuccess: () => {
      toast.success('Rol actualizado exitosamente');
      queryClient.invalidateQueries({ queryKey: ['usuarios-admin'] });
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.message || 'Error al actualizar el rol');
    },
  });

  const updateHabilitadoMutation = useMutation({
    mutationFn: ({ id, habilitado }: { id: number; habilitado: boolean }) =>
      usuariosService.updateHabilitado(id, habilitado),
    onSuccess: () => {
      toast.success('Estado actualizado exitosamente');
      queryClient.invalidateQueries({ queryKey: ['usuarios-admin'] });
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.message || 'Error al actualizar el estado');
    },
  });

  const roles = ['Administrador', 'Árbitro', 'Jugador'];

  if (error) {
    return (
      <AppLayout>
        <Card>
          <CardContent className="py-12 text-center">
            <Shield className="mx-auto h-12 w-12 text-muted-foreground" />
            <p className="mt-4 text-muted-foreground">
              Endpoint no disponible (pendiente de backend)
            </p>
          </CardContent>
        </Card>
      </AppLayout>
    );
  }

  return (
    <AppLayout>
      <div className="space-y-6">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">Gestión de Usuarios</h1>
          <p className="text-muted-foreground">Administra roles y permisos de usuarios</p>
        </div>

        {/* Search */}
        <Card>
          <CardHeader>
            <CardTitle className="text-lg flex items-center gap-2">
              <Search className="h-5 w-5" />
              Buscar Usuarios
            </CardTitle>
          </CardHeader>
          <CardContent>
            <Input
              placeholder="Buscar por nombre o email..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
          </CardContent>
        </Card>

        {/* Users Table */}
        {isLoading ? (
          <div className="flex justify-center py-12">
            <Loader2 className="h-8 w-8 animate-spin text-primary" />
          </div>
        ) : (
          <Card>
            <CardHeader>
              <CardTitle>Usuarios</CardTitle>
              <CardDescription>Lista de usuarios registrados en el sistema</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="overflow-x-auto">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Nombre</TableHead>
                      <TableHead>Email</TableHead>
                      <TableHead>Rol</TableHead>
                      <TableHead>Estado</TableHead>
                      <TableHead>Acciones</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {data?.content.map((usuario) => (
                      <TableRow key={usuario.id}>
                        <TableCell className="font-medium">{usuario.nombre}</TableCell>
                        <TableCell>{usuario.email}</TableCell>
                        <TableCell>
                          <Select
                            value={usuario.rol}
                            onValueChange={(value) =>
                              updateRolMutation.mutate({ id: usuario.id, rol: value })
                            }
                          >
                            <SelectTrigger className="w-[150px]">
                              <SelectValue />
                            </SelectTrigger>
                            <SelectContent>
                              {roles.map((rol) => (
                                <SelectItem key={rol} value={rol}>
                                  {rol}
                                </SelectItem>
                              ))}
                            </SelectContent>
                          </Select>
                        </TableCell>
                        <TableCell>
                          {usuario.habilitado ? (
                            <Badge className="bg-success">Habilitado</Badge>
                          ) : (
                            <Badge variant="destructive">Deshabilitado</Badge>
                          )}
                        </TableCell>
                        <TableCell>
                          <AlertDialog>
                            <AlertDialogTrigger asChild>
                              <Button variant="outline" size="sm">
                                {usuario.habilitado ? 'Deshabilitar' : 'Habilitar'}
                              </Button>
                            </AlertDialogTrigger>
                            <AlertDialogContent>
                              <AlertDialogHeader>
                                <AlertDialogTitle>
                                  {usuario.habilitado ? 'Deshabilitar' : 'Habilitar'} Usuario
                                </AlertDialogTitle>
                                <AlertDialogDescription>
                                  ¿Estás seguro de que deseas{' '}
                                  {usuario.habilitado ? 'deshabilitar' : 'habilitar'} a{' '}
                                  {usuario.nombre}?
                                </AlertDialogDescription>
                              </AlertDialogHeader>
                              <AlertDialogFooter>
                                <AlertDialogCancel>Cancelar</AlertDialogCancel>
                                <AlertDialogAction
                                  onClick={() =>
                                    updateHabilitadoMutation.mutate({
                                      id: usuario.id,
                                      habilitado: !usuario.habilitado,
                                    })
                                  }
                                >
                                  Confirmar
                                </AlertDialogAction>
                              </AlertDialogFooter>
                            </AlertDialogContent>
                          </AlertDialog>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </div>
            </CardContent>
          </Card>
        )}

        {data && data.content.length === 0 && (
          <Card>
            <CardContent className="py-12 text-center">
              <Shield className="mx-auto h-12 w-12 text-muted-foreground" />
              <p className="mt-4 text-muted-foreground">No se encontraron usuarios</p>
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
