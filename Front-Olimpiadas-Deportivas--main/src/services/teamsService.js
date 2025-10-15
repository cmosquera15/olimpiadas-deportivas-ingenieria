import axios from "axios"

const API_URL = "http://localhost:8080/api/equipos"

export const getTeams = async () => {
  const response = await axios.get(API_URL)
  return response.data
}

export const createTeam = async (team) => {
  const response = await axios.post(API_URL, team)
  return response.data
}

export const getTeamById = async (id) => {
  const response = await axios.get(`${API_URL}/${id}`)
  return response.data
}

export const updateTeam = async (id, team) => {
  const response = await axios.put(`${API_URL}/${id}`, team)
  return response.data
}

export const deleteTeam = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`)
  return response.data
}
