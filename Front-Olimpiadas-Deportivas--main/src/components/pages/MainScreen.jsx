"use client"
import "./MainScreen.css"
import TopBar from "../layout/TopBar"
import TeamsView from "../pages/TeamsView"
import PositionsView from "../pages/PositionsView"
import MatchesView from "../pages/MatchesView"

const MainScreen = ({ currentView, navigateTo, goBack, selectedTournament, setSelectedTournament }) => {
  const renderContent = () => {
    switch (currentView) {
      case "teams":
        return <TeamsView />
      case "positions":
        return <PositionsView />
      case "matches":
        return <MatchesView />
      default:
        return (
          <div className="main-content">
            <div className="welcome-message">
              <h2>Bienvenido al Sistema de Torneos</h2>
              <p>Selecciona una opción del menú superior para comenzar.</p>
            </div>
          </div>
        )
    }
  }

  return (
    <div className="main-screen">
      <TopBar
        navigateTo={navigateTo}
        selectedTournament={selectedTournament}
        setSelectedTournament={setSelectedTournament}
      />

      {currentView !== "main" && (
        <div className="back-button-container">
          <button className="btn-secondary" onClick={goBack}>
            ← Atrás
          </button>
        </div>
      )}

      {renderContent()}
    </div>
  )
}

export default MainScreen
