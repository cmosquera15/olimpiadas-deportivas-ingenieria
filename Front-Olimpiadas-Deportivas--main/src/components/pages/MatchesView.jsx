"use client"

import { useState, useEffect } from "react"
import "./MatchesView.css"
import { getCalendarMatches, updateMatchResult } from "../../services/matchesService"
import EditScoreModal from "../modals/EditScoreModal"
import AddMatchForm from "../forms/AddMatchForm"

const MatchesView = () => {
  const [matches, setMatches] = useState([])
  const [expandedMatch, setExpandedMatch] = useState(null)
  const [selectedMatch, setSelectedMatch] = useState(null)
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [showAddForm, setShowAddForm] = useState(false)

  useEffect(() => {
  getCalendarMatches()
    .then(data => {
      const sorted = [...data].sort((a, b) => {
        const fechaA = new Date(`${a.fecha}T${a.hora}`)
        const fechaB = new Date(`${b.fecha}T${b.hora}`)
        return fechaA - fechaB
      })
      setMatches(sorted)
    })
    .catch(err => console.error("Error cargando partidos:", err))
}, [])

  const toggleMatchDetails = (matchId) => {
    setExpandedMatch(expandedMatch === matchId ? null : matchId)
  }

  const openModal = (match) => {
    setSelectedMatch(match)
    setIsModalOpen(true)
  }

  const closeModal = () => {
    setSelectedMatch(null)
    setIsModalOpen(false)
  }

  const handleSaveResult = async (match, resultados) => {
    try {
      await updateMatchResult(match.id, resultados)
      const updated = await getCalendarMatches()
      setMatches(updated)
      closeModal()
    } catch (err) {
      console.error(err)
      alert("Error guardando el resultado")
    }
  }

  const getStatusBadge = (match) => {
    const fechaHora = new Date(`${match.fecha}T${match.hora}`)
    const now = new Date()

    let status = "scheduled"
    if (fechaHora < now) {
      status = "finished"
    }

    const statusConfig = {
      scheduled: { text: "Programado", class: "status-scheduled" },
      finished: { text: "Terminado", class: "status-finished" },
    }

    const config = statusConfig[status]
    return <span className={`status-badge ${config.class}`}>{config.text}</span>
  }

  const formatDate = (dateString) => {
    if (!dateString) return "Por definir"
    const [year, month, day] = dateString.split("-")
    const date = new Date(year, month - 1, day)
    return date.toLocaleDateString("es-ES", {
      weekday: "long",
      year: "numeric",
      month: "long",
      day: "numeric",
    })
  }


  return (
    <div className="matches-view">
      <div className="container">
        <div className="matches-card">
          <h2>Calendario de Partidos</h2>
          <button className="btn-success" onClick={() => setShowAddForm(true)}>
            + Nuevo Partido
          </button>
          {/* Aquí va el formulario cuando showAddForm = true */}
          {showAddForm && (
            <AddMatchForm
              onSave={(saved) => {
                setMatches([...matches, saved])
                setShowAddForm(false)
              }}
              onCancel={() => setShowAddForm(false)}
            />
          )}
          <div className="matches-list">
            {matches.map((match, index) => {
              const fechaHora = new Date(`${match.fecha}T${match.hora}`)
              const pasado = fechaHora < new Date()

              return (
                <div key={match.id ?? index} className="match-item">
                  <div className="match-header">
                    <div className="match-teams">
                      <div className="team-vs">
                        <span className="team-name">{match.equipos?.[0]?.nombre || "Equipo A"}</span>
                        {pasado && (
                          <span className="score">{match.equipos?.[0]?.puntos ?? "-"}</span>
                        )}
                      </div>
                      <div className="vs-separator">VS</div>
                      <div className="team-vs">
                        <span className="team-name">{match.equipos?.[1]?.nombre || "Equipo B"}</span>
                        {pasado && (
                          <span className="score">{match.equipos?.[1]?.puntos ?? "-"}</span>
                        )}
                      </div>
                    </div>

                    <div className="match-info">
                      <div className="match-datetime">
                        <div className="match-date">{formatDate(match.fecha)}</div>
                        <div className="match-time">{match.hora ?? "Sin hora"}</div>
                      </div>
                      {getStatusBadge(match)}
                    </div>

                    <div className="match-actions">
                      {pasado && (
                        <button
                          className="btn-icon"
                          onClick={() => openModal(match)}
                          title="Editar marcador"
                        >
                          ✎
                        </button>
                      )}
                      <button
                        className="btn-secondary details-btn"
                        onClick={() => toggleMatchDetails(index)}
                      >
                        {expandedMatch === index ? "−" : "+"}
                      </button>
                    </div>
                  </div>

                  {expandedMatch === index && (
                    <div className="match-details">
                      <div className="details-grid">
                        <div className="detail-item">
                          <span className="detail-label">Ubicación:</span>
                          <span className="detail-value">{match.lugar || "Por definir"}</span>
                        </div>
                        <div className="detail-item">
                          <span className="detail-label">Árbitro:</span>
                          <span className="detail-value">{match.arbitro || "Por asignar"}</span>
                        </div>
                        <div className="detail-item">
                          <span className="detail-label">Grupo:</span>
                          <span className="detail-value">{match.grupo || "N/A"}</span>
                        </div>
                        <div className="detail-item">
                          <span className="detail-label">Fase:</span>
                          <span className="detail-value">{match.fase || "N/A"}</span>
                        </div>
                        <div className="detail-item">
                          <span className="detail-label">Observaciones:</span>
                          <span className="detail-value">{match.observaciones || "Ninguna"}</span>
                        </div>
                      </div>
                    </div>
                  )}
                </div>
              )
            })}
          </div>
        </div>
      </div>

      {/* Modal de edición */}
      <EditScoreModal
        isOpen={isModalOpen}
        onClose={closeModal}
        partido={selectedMatch}
        onSave={handleSaveResult}
      />
    </div>
  )
}

export default MatchesView
