import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api/usuarios';

export const completarPerfil = async (formData) => {
  const { data } = await axios.post(`${API_URL}/completar`, formData, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem('jwtToken')}`
    }
  });
  return data;
};
