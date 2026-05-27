import React, { useState } from 'react';
import './Login.css';

export default function Login({ onLoginSuccess }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    // Simulamos un tiempo de carga de 1 segundo para que se vea real
    setTimeout(() => {
      // 🚀 LOGIN DE PRUEBA: Usuario y contraseña quemados en el código
      if (email === 'admin@calculadora.com' && password === '123456') {
        // Guardamos un token falso para engañar a la app y que nos deje pasar
        localStorage.setItem('token', 'token-de-prueba-super-secreto');
        onLoginSuccess();
      } else {
        setError('Credenciales incorrectas. Usa: admin@calculadora.com / 123456');
      }
      setLoading(false);
    }, 1000);
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <h2>¡Bienvenido, Manito!</h2>
          <p>Ingresa tus datos para usar la calculadora financiera</p>
        </div>

        <form onSubmit={handleSubmit} className="login-form">
          {error && <div className="error-badge">{error}</div>}

          <div className="input-group">
            <label htmlFor="email">Correo Electrónico</label>
            <input
              type="email"
              id="email"
              placeholder="admin@calculadora.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="input-group">
            <label htmlFor="password">Contraseña</label>
            <input
              type="password"
              id="password"
              placeholder="123456"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button type="submit" className="login-btn" disabled={loading}>
            {loading ? 'Verificando...' : 'Iniciar Sesión'}
          </button>
        </form>
      </div>
    </div>
  );
}