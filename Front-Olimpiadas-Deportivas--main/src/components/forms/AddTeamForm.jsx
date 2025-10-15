"use client"

import { useState, useEffect} from "react"
import "./AddTeamForm.css"

const AddTeamForm = ({ onSave, onCancel, initialData = null}) => {
  const [teamName, setTeamName] = useState(initialData?.name ?? "")
  const [players, setPlayers] = useState(initialData?.players ?? [{ name: "", document: "", eps: "", email: "" }])

  useEffect(() => {
    if (initialData) {
      setTeamName(initialData.name ?? "")
      setPlayers(initialData.players ?? [{ name: "", document: "", eps: "", email: "" }])
    }
  }, [initialData])

  const addPlayer = () => {
    setPlayers([...players, { name: "", document: "", eps: "", email: "" }])
  }

  const removePlayer = (index) => {
    setPlayers(players.filter((_, i) => i !== index))
  }

  const updatePlayer = (index, field, value) => {
    const updatedPlayers = players.map((player, i) => (i === index ? { ...player, [field]: value } : player))
    setPlayers(updatedPlayers)
  }

  const handleSave = () => {
    if (!teamName.trim()) {
      alert("Por favor ingresa el nombre del equipo")
      return
    }

    const validPlayers = players.filter((player) => player.name.trim() && player.document.trim())

    onSave({
      name: teamName,
      players: validPlayers,
    })
  }

  return (
    <div className="add-team-form">
      <div className="container">
        <div className="form-card">
          <h2>{initialData ? "Editar Equipo" : "Agregar Nuevo Equipo"}</h2>

          <div className="form-group">
            <label className="form-label">Nombre del equipo:</label>
            <input
              type="text"
              className="form-input"
              value={teamName}
              onChange={(e) => setTeamName(e.target.value)}
              placeholder="Ingresa el nombre del equipo"
            />
          </div>

          <div className="players-form-section">
            <div className="section-header">
              <h3>Jugadores</h3>
              <button type="button" className="btn-secondary" onClick={addPlayer}>
                Agregar jugador
              </button>
            </div>

            <div className="players-table-container">
              <table className="table editable-table">
                <thead>
                  <tr>
                    <th>Nombre</th>
                    <th>Documento</th>
                    <th>EPS</th>
                    <th>Correo</th>
                    <th>Acciones</th>
                  </tr>
                </thead>
                <tbody>
                  {players.map((player, index) => (
                    <tr key={index}>
                      <td>
                        <input
                          type="text"
                          className="form-input table-input"
                          value={player.name}
                          onChange={(e) => updatePlayer(index, "name", e.target.value)}
                          placeholder="Nombre del jugador"
                        />
                      </td>
                      <td>
                        <input
                          type="text"
                          className="form-input table-input"
                          value={player.document}
                          onChange={(e) => updatePlayer(index, "document", e.target.value)}
                          placeholder="Documento"
                        />
                      </td>
                      <td>
                        <select
                          className="form-select table-input"
                          value={player.eps}
                          onChange={(e) => updatePlayer(index, "eps", e.target.value)}
                        >
                          <option value="">Seleccionar EPS</option>
                          <option value="Sura">Sura</option>
                          <option value="Sanitas">Sanitas</option>
                          <option value="Compensar">Compensar</option>
                          <option value="Nueva EPS">Nueva EPS</option>
                          <option value="Famisanar">Famisanar</option>
                        </select>
                      </td>
                      <td>
                        <input
                          type="email"
                          className="form-input table-input"
                          value={player.email}
                          onChange={(e) => updatePlayer(index, "email", e.target.value)}
                          placeholder="correo@ejemplo.com"
                        />
                      </td>
                      <td>
                        {players.length > 1 && (
                          <button type="button" className="btn-danger btn-small" onClick={() => removePlayer(index)}>
                            Eliminar
                          </button>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>

          <div className="form-actions">
            <button type="button" className="btn-success" onClick={handleSave}>
              Guardar
            </button>
            <button type="button" className="btn-secondary" onClick={onCancel}>
              Cancelar
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default AddTeamForm
