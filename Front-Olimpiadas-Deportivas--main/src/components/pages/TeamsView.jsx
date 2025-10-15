"use client"

import React, { useState } from "react"
import "./TeamsView.css"
import AddTeamForm from "../forms/AddTeamForm"

const TeamsView = () => {
  const [selectedTeam, setSelectedTeam] = useState("")
  const [showAddForm, setShowAddForm] = useState(false)
  const [teams, setTeams] = useState([
    {
      id: 1,
      name: "Águilas Doradas",
      score: 85,
      players: [
        { name: "Juan Pérez", document: "12345678", eps: "Sura", email: "juan@email.com" },
        { name: "Carlos López", document: "87654321", eps: "Sanitas", email: "carlos@email.com" },
      ],
      infractions: [
        { type: "Falta técnica", count: 2, players: [{ name: "Juan Pérez", match: "vs Tigres" }] },
        { type: "Falta personal", count: 5, players: [{ name: "Carlos López", match: "vs Leones" }] },
      ],
    },
    {
      id: 2,
      name: "Tigres Azules",
      score: 92,
      players: [
        { name: "Miguel Torres", document: "11223344", eps: "Compensar", email: "miguel@email.com" },
        { name: "Pedro Ruiz", document: "44332211", eps: "Sura", email: "pedro@email.com" },
      ],
      infractions: [
        { type: "Falta antideportiva", count: 1, players: [{ name: "Miguel Torres", match: "vs Águilas" }] },
      ],
    },
  ])

  const [showInfractionDetails, setShowInfractionDetails] = useState({})

  const selectedTeamData = teams.find((team) => team.name === selectedTeam)

  const toggleInfractionDetails = (index) => {
    setShowInfractionDetails((prev) => ({
      ...prev,
      [index]: !prev[index],
    }))
  }

  const handleAddTeam = (newTeam) => {
    const teamWithId = {
      ...newTeam,
      id: teams.length + 1,
      score: 0,
      infractions: [],
    }
    setTeams([...teams, teamWithId])
    setShowAddForm(false)
  }

  if (showAddForm) {
    return <AddTeamForm onSave={handleAddTeam} onCancel={() => setShowAddForm(false)} />
  }

  return (
    <div className="teams-view">
      <div className="container">
        <div className="teams-header">
          <button className="btn-primary" onClick={() => setShowAddForm(true)}>
            Agregar equipo
          </button>

          <div className="team-selector">
            <label className="form-label">Nombre del equipo:</label>
            <select className="form-select" value={selectedTeam} onChange={(e) => setSelectedTeam(e.target.value)}>
              <option value="">Seleccionar equipo</option>
              {teams.map((team) => (
                <option key={team.id} value={team.name}>
                  {team.name}
                </option>
              ))}
            </select>
          </div>
        </div>

        {selectedTeamData && (
          <div className="team-details">
            <div className="team-score">
              <div className="score-card">
                <h3>Puntuación</h3>
                <div className="score-value">{selectedTeamData.score}</div>
              </div>
            </div>

            <div className="team-content">
              <div className="players-section">
                <div className="section-header">
                  <h3>Jugadores</h3>
                  <button className="btn-secondary">Agregar jugador</button>
                </div>

                <table className="table">
                  <thead>
                    <tr>
                      <th>Nombre</th>
                      <th>Documento</th>
                      <th>EPS</th>
                      <th>Correo</th>
                    </tr>
                  </thead>
                  <tbody>
                    {selectedTeamData.players.map((player, index) => (
                      <tr key={index}>
                        <td>{player.name}</td>
                        <td>{player.document}</td>
                        <td>{player.eps}</td>
                        <td>{player.email}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>

              <div className="infractions-section">
                <h3>Novedades</h3>

                <table className="table">
                  <thead>
                    <tr>
                      <th>Tipo de falta</th>
                      <th>Número de faltas</th>
                      <th>Acción</th>
                    </tr>
                  </thead>
                  <tbody>
                    {selectedTeamData.infractions.map((infraction, index) => (
                      <React.Fragment key={index}>
                        <tr>
                          <td>{infraction.type}</td>
                          <td>{infraction.count}</td>
                          <td>
                            <button className="btn-secondary" onClick={() => toggleInfractionDetails(index)}>
                              {showInfractionDetails[index] ? "Ocultar" : "Ver detalles"}
                            </button>
                          </td>
                        </tr>
                        {showInfractionDetails[index] && (
                          <tr className="infraction-details">
                            <td colSpan="3">
                              <div className="details-table">
                                <table className="table">
                                  <thead>
                                    <tr>
                                      <th>Nombre del jugador</th>
                                      <th>Partido</th>
                                    </tr>
                                  </thead>
                                  <tbody>
                                    {infraction.players.map((player, playerIndex) => (
                                      <tr key={playerIndex}>
                                        <td>{player.name}</td>
                                        <td>{player.match}</td>
                                      </tr>
                                    ))}
                                  </tbody>
                                </table>
                              </div>
                            </td>
                          </tr>
                        )}
                      </React.Fragment>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}

export default TeamsView
