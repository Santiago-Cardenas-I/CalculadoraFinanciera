Feature:  Calculadora Financiera

  Scenario: Calcular cuota con valores validos
    Given que el usuario se encuentra en la calculadora financiera
    When el usuario ingresa un monto de 10000
      And Una tasa de interes del 5%
      And un plazo de 12 meses
    Then el resultado de la cuota mensual debe ser 856.07

  Scenario: Mostratr error al ingresar un monto no permitido
    Given que el usuario se encuentra en la calculadora financiera
    When el usuario ingresa un monto de -5000, una tasa de interes del 5% y un plazo de 12 meses
    Then el sistema debe mostrar un mensaje de error indicando que el monto debe ser mayor a cero

  Scenario: Permitir al usuario realizar un nuevo calculo
    Given el usuario calculo una cuota con un monto de 1000000, una tasa de 12% y un plazo de 12 meses
    When el usuario realize un nuevo calculo  con un monto de 2000000, una tasa de 10% y un plazo de 24 meses
    Then el sistema debe retornar la nueva cuota mensual sin errores

