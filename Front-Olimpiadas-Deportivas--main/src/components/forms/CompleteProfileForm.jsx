import { useState } from "react";
import { completarPerfil } from "../../services/userService";

const CompleteProfileForm = ({ user, onComplete }) => {
  const [formData, setFormData] = useState({
    documento: "",
    programaAcademico: "",
    eps: "",
    rol: ""
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await completarPerfil(formData);
      onComplete();
    } catch (err) {
      console.error("Error al completar el perfil:", err);
    }
  };

  return (
    <div className="complete-profile-container">
      <h2>Completa tu información</h2>
      <div className="user-summary">
        <img src={user.pictureUrl} alt="Foto de perfil" width="80" />
        <p><strong>Nombre:</strong> {user.name}</p>
        <p><strong>Correo:</strong> {user.email}</p>
      </div>

      <form onSubmit={handleSubmit} className="profile-form">
        <label>
          Documento:
          <input type="text" name="documento" value={formData.documento} onChange={handleChange} required />
        </label>
        <label>
          Programa Académico:
          <input type="text" name="programaAcademico" value={formData.programaAcademico} onChange={handleChange} required />
        </label>
        <label>
          EPS:
          <input type="text" name="eps" value={formData.eps} onChange={handleChange} required />
        </label>
        <label>
          Rol:
          <input type="text" name="rol" value={formData.rol} onChange={handleChange} required />
        </label>
        <button type="submit">Guardar y continuar</button>
      </form>
    </div>
  );
};

export default CompleteProfileForm;
