"use client"

import { useState } from "react"
import "./App.css"
import LoginPage from "./components/pages/LoginPage"
import MainScreen from "./components/pages/MainScreen"

function App() {

  const [currentView, setCurrentView] = useState("login")
  const [selectedTournament, setSelectedTournament] = useState("")

  const navigateTo = (view) => {
    setCurrentView(view)
  }

  const goBack = () => {
    if (currentView === "teams" || currentView === "matches" || currentView === "positions") {
      setCurrentView("main")
    } else if (currentView === "main") {
      setCurrentView("login")
    }
  }

  return (
    <div className="App">
      {currentView === "login" && <LoginPage onLogin={() => navigateTo("main")} />}
      {currentView !== "login" && (
        <MainScreen
          currentView={currentView}
          navigateTo={navigateTo}
          goBack={goBack}
          selectedTournament={selectedTournament}
          setSelectedTournament={setSelectedTournament}
        />
      )}
    </div>
  )
}

export default App
