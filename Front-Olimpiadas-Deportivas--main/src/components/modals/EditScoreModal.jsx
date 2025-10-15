"use client"

import { useState } from "react"
//import "./EditScoreModal.css"

const EditScoreModal = ({ isOpen, onClose, partido, onSave }) => {
  if (!isOpen || !partido) return null

  // Inicializar con datos de equipos
  const [resultados, setResultados] = useState(
    partido.equipos.map(eq => ({
      nombre: eq.nombre,
      puntos: eq.puntos ?? 0,
      resultado: eq.resultado ?? ""
    }))
  )

  const handleChange = (index, field, value) => {
    const nuevos = [...resultados]
    nuevos[index][field] = field === "puntos" ? parseInt(value, 10) : value
    setResultados(nuevos)
  }

  const handleSave = () => {
    onSave(partido, resultados)
  }

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>Editar marcador</h2>

        {resultados.map((eq, index) => (
          <div key={eq.nombre} className="team-result">
            <strong>{eq.nombre}</strong>
            <input
              type="number"
              value={eq.puntos}
              onChange={(e) => handleChange(index, "puntos", e.target.value)}
              placeholder="Puntos"
            />
            <select
              value={eq.resultado}
              onChange={(e) => handleChange(index, "resultado", e.target.value)}
            >
              <option value="">-- Resultado --</option>
              <option value="Ganado">Ganado</option>
              <option value="Perdido">Perdido</option>
              <option value="Empatado">Empatado</option>
            </select>
          </div>
        ))}

        <div className="modal-actions">
          <button className="btn-success" onClick={handleSave}>Guardar</button>
          <button className="btn-secondary" onClick={onClose}>Cancelar</button>
        </div>
      </div>
    </div>
  )
}

export default EditScoreModal
