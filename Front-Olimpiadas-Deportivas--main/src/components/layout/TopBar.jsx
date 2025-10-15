"use client"
import "./TopBar.css"

const TopBar = ({ navigateTo, selectedTournament, setSelectedTournament }) => {
  const tournaments = ["Torneo de Baloncesto 2024", "Torneo de Fútbol 2024", "Copa Mixta 2024"]

  return (
    <div className="top-bar">
      <div className="top-bar-left">
        <label className="tournament-label">Torneo:</label>
        <select
          className="tournament-select"
          value={selectedTournament}
          onChange={(e) => setSelectedTournament(e.target.value)}
        >
          <option value="">Seleccionar torneo</option>
          {tournaments.map((tournament, index) => (
            <option key={index} value={tournament}>
              {tournament}
            </option>
          ))}
        </select>
      </div>

      <div className="top-bar-right">
        <button className="btn-primary nav-btn" onClick={() => navigateTo("teams")}>
          Equipos
        </button>
        <button className="btn-primary nav-btn" onClick={() => navigateTo("matches")}>
          Partidos
        </button>
        <button className="btn-primary nav-btn" onClick={() => navigateTo("positions")}>
          Posiciones
        </button>
      </div>
    </div>
  )
}

export default TopBar
