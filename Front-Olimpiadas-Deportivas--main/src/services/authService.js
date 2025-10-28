import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api/auth';

axios.interceptors.request.use((config) => {
    const token = localStorage.getItem('jwtToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export const loginWithGoogle = async (token) => {
  try {
    const { data } = await axios.post(`${API_URL}/google-login`, { token });
    localStorage.setItem('jwtToken', data.token);
    return data;
  } catch (error) {
    console.error('Error en loginWithGoogle:', error);
    throw error;
  }
};

export const logout = () => {
  localStorage.removeItem('jwtToken');
};
