import axios from "axios"

const API_URL = "http://localhost:8080/api/partidos"

export const getMatches = async () => {
  const response = await axios.get(API_URL)
  return response.data
}

export const getCalendarMatches = async () => {
  const response = await axios.get(`${API_URL}/calendario`)
  return response.data
}

export const createMatch = async (match) => {
  const response = await axios.post(API_URL, match)
  return response.data
}

export const updateMatch = async (id, match) => {
  const response = await axios.put(`${API_URL}/${id}`, match);
  return response.data;
};

export const deleteMatch = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`)
  return response.data
}

export const updateMatchResult = async (id, resultados) => {
  const response = await axios.put(`${API_URL}/${id}/resultado`, { resultados })
  return response.data
}
