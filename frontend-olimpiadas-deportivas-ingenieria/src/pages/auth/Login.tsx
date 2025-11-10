import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { GoogleLogin, GoogleOAuthProvider } from '@react-oauth/google';
import { useMutation } from '@tanstack/react-query';
import { authService } from '@/services/auth.service';
import { useAuth } from '@/store/useAuth';
import { toast } from 'sonner';
import { Trophy } from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';

const GOOGLE_CLIENT_ID = import.meta.env.VITE_GOOGLE_CLIENT_ID;

export default function Login() {
  const navigate = useNavigate();
  const { setAuth, isAuthenticated } = useAuth();

  useEffect(() => {
    if (isAuthenticated) {
      navigate('/dashboard');
    }
  }, [isAuthenticated, navigate]);

  const loginMutation = useMutation({
    mutationFn: authService.googleLogin,
    onSuccess: (data) => {
      if (!data.completo) {
        setAuth(data.jwt, data.usuario!);
        navigate('/auth/completar-perfil');
      } else {
        setAuth(data.jwt, data.usuario!);
        toast.success('Inicio de sesión exitoso');
        navigate('/dashboard');
      }
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.message || 'Error al iniciar sesión');
    },
  });

  const handleGoogleSuccess = (credentialResponse: any) => {
    if (credentialResponse.credential) {
      loginMutation.mutate(credentialResponse.credential);
    }
  };

  const handleGoogleError = () => {
    toast.error('Error al autenticar con Google');
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-gradient-to-br from-primary/5 via-background to-secondary/5 p-4">
      <Card className="w-full max-w-md">
        <CardHeader className="space-y-4 text-center">
          <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-full bg-primary/10">
            <Trophy className="h-8 w-8 text-primary" />
          </div>
          <CardTitle className="text-2xl">Olimpiadas Deportivas</CardTitle>
          <CardDescription className="text-base">
            Ingeniería - Universidad de Antioquia
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-4">
            <p className="text-center text-sm text-muted-foreground">
              Inicia sesión con tu cuenta de Google para acceder al sistema
            </p>
            <div className="flex justify-center">
              {GOOGLE_CLIENT_ID ? (
                <GoogleOAuthProvider clientId={GOOGLE_CLIENT_ID}>
                  <GoogleLogin
                    onSuccess={handleGoogleSuccess}
                    onError={handleGoogleError}
                    text="signin_with"
                    locale="es"
                  />
                </GoogleOAuthProvider>
              ) : (
                <div className="text-center text-sm text-destructive">
                  Error: No se ha configurado VITE_GOOGLE_CLIENT_ID
                </div>
              )}
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
