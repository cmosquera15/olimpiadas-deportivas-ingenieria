"use client"

import { useState, useEffect } from "react"
import { createMatch } from "../../services/matchesService"
import { getTeams } from "../../services/teamsService"
//import "./AddMatchForm.css"

const AddMatchForm = ({ onSave, onCancel }) => {
  const [fecha, setFecha] = useState("")
  const [hora, setHora] = useState("")
  const [lugar, setLugar] = useState("")
  const [fase, setFase] = useState("")
  const [jornada, setJornada] = useState("")
  const [torneo, setTorneo] = useState("")
  const [grupo, setGrupo] = useState("")
  const [arbitro, setArbitro] = useState("")
  const [observaciones, setObservaciones] = useState("")
  const [equipos, setEquipos] = useState([])
  const [selectedTeams, setSelectedTeams] = useState([])

  // Cargar equipos desde backend
  useEffect(() => {
    getTeams()
      .then(setEquipos)
      .catch(err => console.error("Error cargando equipos:", err))
  }, [])

  const handleTeamSelect = (id) => {
    if (selectedTeams.includes(id)) {
      setSelectedTeams(selectedTeams.filter(teamId => teamId !== id))
    } else if (selectedTeams.length < 2) {
      setSelectedTeams([...selectedTeams, id])
    } else {
      alert("Solo puedes seleccionar 2 equipos por partido")
    }
  }

  const handleSave = async () => {
    if (!fecha || !hora || selectedTeams.length !== 2) {
      alert("Completa la fecha, hora y selecciona 2 equipos")
      return
    }

    const newMatch = {
      fecha,
      hora,
      lugar,
      fase,
      jornada,
      torneo,
      grupo,
      arbitro,
      observaciones,
      equipos: selectedTeams.map(teamId => ({ id: teamId })),
    }

    try {
      const saved = await createMatch(newMatch)
      onSave(saved)
    } catch (error) {
      console.error("Error creando partido:", error)
      alert("Error guardando el partido")
    }
  }

  return (
    <div className="add-match-form">
      <div className="container">
        <div className="form-card">
          <h2>Agregar Nuevo Partido</h2>

          <div className="form-group">
            <label>Fecha:</label>
            <input type="date" value={fecha} onChange={(e) => setFecha(e.target.value)} />
          </div>

          <div className="form-group">
            <label>Hora:</label>
            <input type="time" value={hora} onChange={(e) => setHora(e.target.value)} />
          </div>

          <div className="form-group">
            <label>Lugar:</label>
            <input type="text" value={lugar} onChange={(e) => setLugar(e.target.value)} placeholder="Ej: Cancha UdeA" />
          </div>

          <div className="form-group">
            <label>Fase:</label>
            <input type="text" value={fase} onChange={(e) => setFase(e.target.value)} />
          </div>

          <div className="form-group">
            <label>Jornada:</label>
            <input type="text" value={jornada} onChange={(e) => setJornada(e.target.value)} />
          </div>

          <div className="form-group">
            <label>Torneo:</label>
            <input type="text" value={torneo} onChange={(e) => setTorneo(e.target.value)} />
          </div>

          <div className="form-group">
            <label>Grupo:</label>
            <input type="text" value={grupo} onChange={(e) => setGrupo(e.target.value)} />
          </div>

          <div className="form-group">
            <label>Árbitro:</label>
            <input type="text" value={arbitro} onChange={(e) => setArbitro(e.target.value)} />
          </div>

          <div className="form-group">
            <label>Observaciones:</label>
            <textarea value={observaciones} onChange={(e) => setObservaciones(e.target.value)} />
          </div>

            <div className="form-group">
              <label>Equipo 1:</label>
              <select
                value={selectedTeams[0] ?? ""}
                onChange={(e) => {
                  const id = e.target.value
                  setSelectedTeams([id, selectedTeams[1] ?? ""])
                }}
              >
                <option value="">-- Selecciona un equipo --</option>
                {equipos.map((team) => (
                  <option key={team.id} value={team.id}>
                    {team.nombre}
                  </option>
                ))}
              </select>
            </div>

            <div className="form-group">
              <label>Equipo 2:</label>
              <select
                value={selectedTeams[1] ?? ""}
                onChange={(e) => {
                  const id = e.target.value
                  setSelectedTeams([selectedTeams[0] ?? "", id])
                }}
              >
                <option value="">-- Selecciona un equipo --</option>
                {equipos.map((team) => (
                  <option key={team.id} value={team.id}>
                    {team.nombre}
                  </option>
                ))}
              </select>
            </div>

          <div className="form-actions">
            <button className="btn-success" onClick={handleSave}>Guardar</button>
            <button className="btn-secondary" onClick={onCancel}>Cancelar</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default AddMatchForm
