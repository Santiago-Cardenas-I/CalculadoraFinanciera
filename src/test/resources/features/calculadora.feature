Feature: Uso del Simulador Financiero
  Como usuario de la aplicación
  Quiero poder iniciar sesión con credenciales válidas
  Para acceder a la calculadora financiera y simular una cuota

  Scenario: Flujo completo de login y cálculo de préstamo
    Given el usuario abre la pagina de login de la calculadora
    When el usuario ingresa el correo "admin@calculadora.com" y la contrasena "123456"
    Then el usuario deberia ver el titulo "Simulador Financiero" en la aplicacion
    When el usuario ingresa un monto de "1000000", un plazo de "12" y una tasa de "15"
    And selecciona el tipo de calculo "cuota"
    And hace clic en el boton "Calcular Simulacion"
    Then el usuario deberia ver los resultados de la "Cuota Fija Mensual"