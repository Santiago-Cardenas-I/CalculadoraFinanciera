import React, { useState, useEffect } from 'react';
import Login from './Login'; // Asegúrate de que Login.jsx esté en la misma carpeta

export default function App() {
  // --- ESTADO DE AUTENTICACIÓN ---
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // --- ESTADOS DE LA CALCULADORA ---
  const [monto, setMonto] = useState('');
  const [tasa, setTasa] = useState(''); // Ahora es Tasa Anual
  const [meses, setMeses] = useState('');
  const [tipoCalculo, setTipoCalculo] = useState('cuota');

  const [resultado, setResultado] = useState(null);
  const [tablaAmortizacion, setTablaAmortizacion] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // --- EFECTO PARA VERIFICAR SESIÓN ---
  // Revisa si ya hay un token guardado al recargar la página
  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    // Limpiamos los datos por seguridad al cerrar sesión
    setResultado(null);
    setTablaAmortizacion([]);
    setMonto('');
    setTasa('');
    setMeses('');
  };

  // --- FUNCIONES DE LA CALCULADORA ---
  const generarTablaFrontend = (montoInicial, tasaAnual, plazo, cuotaFija) => {
    const tasaMensual = (tasaAnual / 100) / 12;
    let saldo = parseFloat(montoInicial);
    const tabla = [];

    for (let i = 1; i <= plazo; i++) {
      const interes = saldo * tasaMensual;
      let capital = cuotaFija - interes;
      saldo -= capital;

      if (saldo < 0) saldo = 0;

      tabla.push({
        mes: i,
        cuota: cuotaFija,
        interes: interes,
        capital: capital,
        saldo: saldo
      });
    }
    return tabla;
  };

  const handleSimular = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setResultado(null);
    setTablaAmortizacion([]);

    // Validación rápida en el cliente
    if (monto <= 0 || tasa < 0 || meses <= 0) {
      setError("Error: El monto y los meses deben ser mayores que 0, y la tasa no puede ser negativa.");
      setLoading(false);
      return;
    }

    const url = `http://localhost:8080/finanzas/${tipoCalculo}?monto=${monto}&tasa=${tasa}&meses=${meses}`;

    try {
      // Obtenemos el token para enviarlo (por si luego tu calculadora también pide auth)
      const token = localStorage.getItem('token');

      const response = await fetch(url, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}` // Opcional: Preparamos la cabecera por si tu backend la pide
        }
      });

      if (!response.ok) {
        throw new Error('Error en el servidor. Verifica los datos ingresados.');
      }

      const valorCalculado = await response.json();
      setResultado(valorCalculado);

      // Si el cálculo es "cuota", generamos la tabla de amortización
      if (tipoCalculo === 'cuota') {
        const tabla = generarTablaFrontend(monto, tasa, meses, valorCalculado);
        setTablaAmortizacion(tabla);
      }

    } catch (err) {
      setError(err.message || 'Ocurrió un error inesperado al conectar con el backend.');
    } finally {
      setLoading(false);
    }
  };

  const formatearMoneda = (valor) => {
    return '$' + Number(valor).toLocaleString('es-CO', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
  };

  // --- RENDERIZADO CONDICIONAL ---
  // Si no está logueado, mostramos directamente la pantalla de Login
  if (!isLoggedIn) {
    return <Login onLoginSuccess={() => setIsLoggedIn(true)} />;
  }

  // Si está logueado, mostramos la calculadora financiera
  return (
    <div style={styles.container}>
      {/* Barra superior para el botón de Cerrar Sesión */}
      <div style={styles.headerBar}>
        <button onClick={handleLogout} style={styles.logoutButton}>
          Cerrar Sesión
        </button>
      </div>

      <div style={styles.card}>
        <h2 style={styles.title}>Simulador Financiero</h2>

        <form onSubmit={handleSimular} style={styles.form}>
          <div style={styles.inputGroup}>
            <label style={styles.label}>Monto del Préstamo ($)</label>
            <input type="number" value={monto} onChange={(e) => setMonto(e.target.value)} placeholder="Ej. 1000000" required style={styles.input} />
          </div>

          <div style={styles.inputGroup}>
            <label style={styles.label}>Tasa de Interés (% Anual)</label>
            <input type="number" step="0.01" value={tasa} onChange={(e) => setTasa(e.target.value)} placeholder="Ej. 24" required style={styles.input} />
          </div>

          <div style={styles.inputGroup}>
            <label style={styles.label}>Plazo (Meses)</label>
            <input type="number" value={meses} onChange={(e) => setMeses(e.target.value)} placeholder="Ej. 12" required style={styles.input} />
          </div>

          <div style={styles.inputGroup}>
            <label style={styles.label}>Tipo de Cálculo</label>
            <select value={tipoCalculo} onChange={(e) => setTipoCalculo(e.target.value)} style={styles.select}>
              <option value="cuota">Cuota Mensual (Amortización)</option>
              <option value="interes-simple">Interés Simple</option>
              <option value="interes-compuesto">Interés Compuesto</option>
            </select>
          </div>

          <button type="submit" disabled={loading} style={styles.button}>
            {loading ? 'Procesando...' : 'Calcular Simulación'}
          </button>
        </form>

        {/* Manejo de Errores Visuales */}
        {error && (
          <div style={styles.errorBanner}>
            <strong> Atención:</strong> {error}
          </div>
        )}
      </div>

      {/* Bloque de Resultados Condicionales */}
      {resultado !== null && !error && (
        <div style={styles.resultsContainer}>
          <div style={styles.summaryCards}>
            <div style={styles.mainCard}>
              <h3 style={styles.cardTitle}>
                {tipoCalculo === 'cuota' ? 'Cuota Fija Mensual' :
                 tipoCalculo === 'interes-simple' ? 'Interés Total Generado' : 'Monto Final Acumulado'}
              </h3>
              <p style={styles.mainValue}>{formatearMoneda(resultado)}</p>
            </div>

            {tipoCalculo === 'cuota' && (
              <div style={styles.secondaryCard}>
                <h3 style={styles.cardTitle}>Total Intereses a Pagar</h3>
                <p style={styles.secondaryValue}>{formatearMoneda((resultado * meses) - monto)}</p>
              </div>
            )}
          </div>

          {/* Tabla de Amortización (Solo visible para "cuota") */}
          {tipoCalculo === 'cuota' && tablaAmortizacion.length > 0 && (
            <div style={styles.tableWrapper}>
              <h3 style={styles.tableTitle}>Tabla de Amortización</h3>
              <table style={styles.table}>
                <thead>
                  <tr style={styles.tableHeader}>
                    <th style={styles.th}>Mes</th>
                    <th style={styles.th}>Cuota</th>
                    <th style={styles.th}>Interés</th>
                    <th style={styles.th}>Capital</th>
                    <th style={styles.th}>Saldo</th>
                  </tr>
                </thead>
                <tbody>
                  {tablaAmortizacion.map((fila) => (
                    <tr key={fila.mes} style={styles.tableRow}>
                      <td style={styles.td}><strong>{fila.mes}</strong></td>
                      <td style={styles.td}>{formatearMoneda(fila.cuota)}</td>
                      <td style={styles.td}>{formatearMoneda(fila.interes)}</td>
                      <td style={styles.td}>{formatearMoneda(fila.capital)}</td>
                      <td style={styles.td}>{formatearMoneda(fila.saldo)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      )}
    </div>
  );
}

// Estilos
const styles = {
  container: { display: 'flex', flexDirection: 'column', alignItems: 'center', minHeight: '100vh', backgroundColor: '#f3f4f6', fontFamily: 'system-ui, sans-serif', padding: '20px', gap: '20px' },
  headerBar: { width: '100%', maxWidth: '800px', display: 'flex', justifyContent: 'flex-end' },
  logoutButton: { backgroundColor: '#dc2626', color: '#ffffff', padding: '10px 20px', borderRadius: '8px', border: 'none', fontSize: '14px', fontWeight: 'bold', cursor: 'pointer', transition: 'background-color 0.2s', boxShadow: '0 2px 4px rgba(0,0,0,0.1)' },
  card: { backgroundColor: '#ffffff', padding: '30px', borderRadius: '12px', boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)', width: '100%', maxWidth: '450px' },
  title: { textAlign: 'center', color: '#1f2937', marginBottom: '24px', fontSize: '24px' },
  form: { display: 'flex', flexDirection: 'column', gap: '16px' },
  inputGroup: { display: 'flex', flexDirection: 'column', gap: '6px' },
  label: { fontSize: '14px', fontWeight: '600', color: '#4b5563' },
  input: { padding: '10px', borderRadius: '6px', border: '1px solid #d1d5db', fontSize: '16px' },
  select: { padding: '10px', borderRadius: '6px', border: '1px solid #d1d5db', fontSize: '16px', backgroundColor: '#fff' },
  button: { backgroundColor: '#2563eb', color: '#ffffff', padding: '12px', borderRadius: '6px', border: 'none', fontSize: '16px', fontWeight: '600', cursor: 'pointer', transition: 'background-color 0.2s', marginTop: '8px' },
  errorBanner: { backgroundColor: '#fee2e2', color: '#991b1b', border: '1px solid #f87171', padding: '12px', borderRadius: '6px', marginTop: '20px', fontSize: '14px' },

  // Estilos de Resultados y Tabla
  resultsContainer: { width: '100%', maxWidth: '800px', display: 'flex', flexDirection: 'column', gap: '20px' },
  summaryCards: { display: 'flex', gap: '20px', flexWrap: 'wrap', justifyContent: 'center' },
  mainCard: { flex: '1', minWidth: '250px', backgroundColor: '#ecfdf5', border: '1px solid #10b981', borderRadius: '12px', padding: '20px', textAlign: 'center', boxShadow: '0 2px 4px rgba(0,0,0,0.05)' },
  secondaryCard: { flex: '1', minWidth: '250px', backgroundColor: '#fff7ed', border: '1px solid #f97316', borderRadius: '12px', padding: '20px', textAlign: 'center', boxShadow: '0 2px 4px rgba(0,0,0,0.05)' },
  cardTitle: { margin: '0 0 10px 0', fontSize: '16px', color: '#374151' },
  mainValue: { margin: '0', fontSize: '32px', fontWeight: 'bold', color: '#047857' },
  secondaryValue: { margin: '0', fontSize: '28px', fontWeight: 'bold', color: '#c2410c' },

  tableWrapper: { backgroundColor: '#ffffff', borderRadius: '12px', padding: '20px', boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)', overflowX: 'auto' },
  tableTitle: { textAlign: 'center', color: '#1f2937', marginBottom: '20px', fontSize: '20px' },
  table: { width: '100%', borderCollapse: 'collapse', fontSize: '14px' },
  tableHeader: { backgroundColor: '#f9fafb', borderBottom: '2px solid #e5e7eb' },
  th: { padding: '12px', textAlign: 'right', color: '#4b5563', fontWeight: '600' },
  tableRow: { borderBottom: '1px solid #e5e7eb' },
  td: { padding: '12px', textAlign: 'right', color: '#1f2937' }
};