"use client"
import "./LoginPage.css"

const LoginPage = ({ onLogin }) => {
  return (
    <div className="login-container">
      <div className="login-card">
        <h1 className="login-title">Olimpiadas deportivas</h1>
        <button className="btn-primary login-btn" onClick={onLogin}>
          Iniciar sesión
        </button>
      </div>
    </div>
  )
}

export default LoginPage
