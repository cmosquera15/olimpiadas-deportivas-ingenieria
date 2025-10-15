import App from "../src/App"
import React from 'react'
import ReactDOM from 'react-dom/client'

export default function Page() {
  return <App />
}
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
)
