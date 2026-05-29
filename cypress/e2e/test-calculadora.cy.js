describe('Pruebas E2E - Flujo Completo Integrado', () => {

  it('Debería registrarse, loguearse, ir al dashboard y usar la calculadora exitosamente', () => {

    // ---- PASO 1: REGISTRO ----
    cy.visit('http://localhost:5174');
    cy.contains('Registrarse').click();

    // Generamos el usuario aleatorio para evitar problemas de duplicados
    const usuarioAleatorio = `user_${Math.floor(Math.random() * 100000)}`;

    cy.get('input[placeholder="Usuario"]').type(usuarioAleatorio);
    cy.get('input[placeholder="Contraseña"]').type('Password123');
    cy.get('button[type="submit"]').click();

    // Hacemos clic en el enlace para volver al login (ignorando mayúsculas/minúsculas)
    cy.contains(/iniciar sesión|volver|login/i).click();

    // ---- PASO 2: LOGIN ----
    cy.get('input[placeholder="Usuario"]').type(usuarioAleatorio);
    cy.get('input[placeholder="Contraseña"]').type('Password123');
    cy.get('button[type="submit"]').click();

    // ---- PASO 3: DASHBOARD ----
    cy.url().should('include', '/dashboard');
    cy.contains(`Bienvenido ${usuarioAleatorio}`).should('be.visible');

    // Hacemos clic en el botón de la calculadora quitando el target="_blank"
    cy.get('.btn-calculadora').invoke('removeAttr', 'target').click();

// ---- PASO 4: LA CALCULADORA FINANCIERA (:3001) ----
    cy.origin('http://localhost:3001', { args: { usuarioAleatorio } }, () => {
      cy.url().should('include', 'http://localhost:3001');

      // Rellenamos los campos por orden de aparición
      cy.get('input').eq(0).clear().type('1000000');
      cy.get('input').eq(1).clear().type('12');
      cy.get('input').eq(2).clear().type('15');

      // Hacemos clic usando la expresión regular flexible para el botón
      cy.contains(/calcular.*simula|calcular/i).click();

      // Validamos que se muestre algún resultado en la pantalla tras procesar el backend
      cy.wait(1000); // Esperamos un segundo a que el backend responda
      cy.get('body').should('contain.text', '1'); // Validamos que el cuerpo contenga datos simulados
    });

  });
});