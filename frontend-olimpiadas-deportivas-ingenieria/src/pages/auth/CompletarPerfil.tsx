import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { useMutation } from '@tanstack/react-query';
import { authService } from '@/services/auth.service';
import { useCatalogos } from '@/hooks/useCatalogos';
import { useAuth } from '@/store/useAuth';
import { toast } from 'sonner';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Loader2 } from 'lucide-react';

const formSchema = z.object({
  documento: z.string().min(1, 'El documento es requerido'),
  id_eps: z.coerce.number({ required_error: 'La EPS es requerida' }),
  id_programa_academico: z.coerce.number({ required_error: 'El programa académico es requerido' }),
  id_genero: z.coerce.number({ required_error: 'El género es requerido' }),
  id_tipo_vinculo: z.coerce.number({ required_error: 'El tipo de vínculo es requerido' }),
});

export default function CompletarPerfil() {
  const navigate = useNavigate();
  const { setAuth } = useAuth();
  const { programas, eps, generos, tiposVinculo } = useCatalogos();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
  });

  const completarPerfilMutation = useMutation({
    mutationFn: authService.completarPerfil,
    onSuccess: (data) => {
      setAuth(data.jwt, data.usuario!);
      toast.success('Perfil completado exitosamente');
      navigate('/dashboard');
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.message || 'Error al completar el perfil');
    },
  });

  const onSubmit = (values: z.infer<typeof formSchema>) => {
    completarPerfilMutation.mutate({
      documento: values.documento,
      id_eps: values.id_eps,
      id_programa_academico: values.id_programa_academico,
      id_genero: values.id_genero,
      id_tipo_vinculo: values.id_tipo_vinculo,
    });
  };

  const isLoading =
    programas.isLoading || eps.isLoading || generos.isLoading || tiposVinculo.isLoading;

  if (isLoading) {
    return (
      <div className="flex min-h-screen items-center justify-center">
        <Loader2 className="h-8 w-8 animate-spin text-primary" />
      </div>
    );
  }

  return (
    <div className="flex min-h-screen items-center justify-center bg-gradient-to-br from-primary/5 via-background to-secondary/5 p-4">
      <Card className="w-full max-w-2xl">
        <CardHeader>
          <CardTitle>Completar Perfil</CardTitle>
          <CardDescription>
            Por favor completa tu información para acceder al sistema
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              <FormField
                control={form.control}
                name="documento"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Documento de Identidad</FormLabel>
                    <FormControl>
                      <Input placeholder="1234567890" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="id_programa_academico"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Programa Académico</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      value={field.value?.toString()}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecciona tu programa" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {programas.data?.map((programa) => (
                          <SelectItem key={programa.id} value={programa.id.toString()}>
                            {programa.nombre}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="id_eps"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>EPS</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      value={field.value?.toString()}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecciona tu EPS" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {eps.data?.map((epsItem) => (
                          <SelectItem key={epsItem.id} value={epsItem.id.toString()}>
                            {epsItem.nombre}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="id_genero"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Género</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      value={field.value?.toString()}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecciona tu género" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {generos.data?.map((genero) => (
                          <SelectItem key={genero.id} value={genero.id.toString()}>
                            {genero.nombre}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="id_tipo_vinculo"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Tipo de Vínculo</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      value={field.value?.toString()}
                    >
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Selecciona tu tipo de vínculo" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {tiposVinculo.data?.map((tipo) => (
                          <SelectItem key={tipo.id} value={tipo.id.toString()}>
                            {tipo.nombre}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <Button
                type="submit"
                className="w-full"
                disabled={completarPerfilMutation.isPending}
              >
                {completarPerfilMutation.isPending && (
                  <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                )}
                Completar Perfil
              </Button>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
}
