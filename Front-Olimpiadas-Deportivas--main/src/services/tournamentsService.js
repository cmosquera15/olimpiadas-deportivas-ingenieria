import axios from "axios";

const API_URL = "http://localhost:8080/api/torneos";

export const getTournaments = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const getTournamentsByName = async (name) => {
  const response = await axios.get(`${API_URL}/nombres`, {
    params: { nombre: name },
  });
  return response.data;
}

export const createTournament = async (tournamentData) => {
  const response = await axios.post(API_URL, tournamentData);
  return response.data;
};

export const updateTournament = async (id, tournamentData) => {
  const response = await axios.put(`${API_URL}/${id}`, tournamentData);
  return response.data;
}

export const deleteTournament = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`);
  return response.data;
};