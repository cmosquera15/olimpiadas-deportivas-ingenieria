"use client"

import { useState } from "react"
import "./PositionsView.css"

const PositionsView = () => {
  const [positions] = useState([
    {
      id: 1,
      teamName: "Tigres Azules",
      points: 15,
      gamesPlayed: 8,
      won: 5,
      lost: 2,
      tied: 1,
    },
    {
      id: 2,
      teamName: "Águilas Doradas",
      points: 12,
      gamesPlayed: 7,
      won: 4,
      lost: 2,
      tied: 1,
    },
    {
      id: 3,
      teamName: "Leones Rojos",
      points: 10,
      gamesPlayed: 6,
      won: 3,
      lost: 2,
      tied: 1,
    },
    {
      id: 4,
      teamName: "Panteras Negras",
      points: 9,
      gamesPlayed: 7,
      won: 3,
      lost: 4,
      tied: 0,
    },
    {
      id: 5,
      teamName: "Halcones Verdes",
      points: 6,
      gamesPlayed: 6,
      won: 2,
      lost: 4,
      tied: 0,
    },
    {
      id: 6,
      teamName: "Lobos Grises",
      points: 4,
      gamesPlayed: 5,
      won: 1,
      lost: 3,
      tied: 1,
    },
  ])

  // Sort teams by points (descending), then by games won
  const sortedPositions = [...positions].sort((a, b) => {
    if (b.points !== a.points) {
      return b.points - a.points
    }
    return b.won - a.won
  })

  return (
    <div className="positions-view">
      <div className="container">
        <div className="positions-card">
          <h2>Tabla de Posiciones</h2>

          <div className="positions-table-container">
            <table className="table positions-table">
              <thead>
                <tr>
                  <th className="position-col">Pos</th>
                  <th className="team-col">Nombre del equipo</th>
                  <th className="points-col">Puntos</th>
                  <th className="games-col">Partidos jugados</th>
                  <th className="won-col">Ganados</th>
                  <th className="lost-col">Perdidos</th>
                  <th className="tied-col">Empatados</th>
                </tr>
              </thead>
              <tbody>
                {sortedPositions.map((team, index) => (
                  <tr key={team.id} className={`position-row ${index < 3 ? "top-position" : ""}`}>
                    <td className="position-number">
                      <span
                        className={`position-badge ${index === 0 ? "first" : index === 1 ? "second" : index === 2 ? "third" : ""}`}
                      >
                        {index + 1}
                      </span>
                    </td>
                    <td className="team-name">{team.teamName}</td>
                    <td className="points-value">
                      <strong>{team.points}</strong>
                    </td>
                    <td>{team.gamesPlayed}</td>
                    <td className="won-value">{team.won}</td>
                    <td className="lost-value">{team.lost}</td>
                    <td className="tied-value">{team.tied}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="positions-summary">
            <div className="summary-stats">
              <div className="stat-item">
                <span className="stat-label">Total de equipos:</span>
                <span className="stat-value">{positions.length}</span>
              </div>
              <div className="stat-item">
                <span className="stat-label">Partidos totales:</span>
                <span className="stat-value">
                  {Math.floor(positions.reduce((sum, team) => sum + team.gamesPlayed, 0) / 2)}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default PositionsView
