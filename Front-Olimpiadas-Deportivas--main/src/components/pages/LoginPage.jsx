import { GoogleLogin } from '@react-oauth/google';
import { loginWithGoogle } from '../../services/authService';

const LoginPage = () => {

  const handleLoginSuccess = async (response) => {
    const token = response.credential;
    if (!token) {
      console.error("No se recibió un token válido");
      return;
    }

    try {
      const data = await loginWithGoogle(token);
      console.log('Login exitoso', data);
      localStorage.setItem("user", JSON.stringify(data));
      onLogin();
    } catch (error) {
      console.error('Error al iniciar sesión:', error);
    }
  };

  const handleLoginError = () => {
    console.error('Error en el inicio de sesión con Google');
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h1 className="login-title">Olimpiadas deportivas</h1>
        <GoogleLogin
          onSuccess={handleLoginSuccess}
          onError={handleLoginError}
          useOneTap
        />
      </div>
    </div>
  );
};

export default LoginPage;
