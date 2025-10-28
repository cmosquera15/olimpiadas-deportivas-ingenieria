"use client"

import { useState, useEffect } from "react"
import "./App.css"
import LoginPage from "./components/pages/LoginPage"
import MainScreen from "./components/pages/MainScreen"
import CompleteProfileForm from "./components/forms/CompleteProfileForm"
import { GoogleOAuthProvider } from '@react-oauth/google'

function App() {

  const [currentView, setCurrentView] = useState("login")
  const [selectedTournament, setSelectedTournament] = useState("")
  const [user, setUser] = useState(null)

  const navigateTo = (view) => {
    setCurrentView(view)
  }

  const goBack = () => {
    if (["teams", "matches", "positions"].includes(currentView)) {
      setCurrentView("main")
    } else {
      setCurrentView("login")
      localStorage.removeItem("user")
      localStorage.removeItem("jwtToken")
    }
  }

  useEffect(() => {
    const userData = localStorage.getItem("user")
    if (userData) {
      const parsed = JSON.parse(userData)
      setUser(parsed)
      setCurrentView(parsed.completo ? "main" : "complete-profile")
    }
  }, [])

  const handleLogin = (data) => {
    setUser(data)
    navigateTo(data.completo ? "main" : "complete-profile")
  }

  return (
    <div className="App">
      {currentView === "login" && <LoginPage onLogin={handleLogin} />}
      {currentView === "complete-profile" && (
        <CompleteProfileForm user={user} onComplete={() => navigateTo("main")} />
      )}
      {currentView === "main" && (
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

function AppWrapper() {
  return (
    <GoogleOAuthProvider clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}>
      <App />
    </GoogleOAuthProvider>
  )
}

export default AppWrapper
