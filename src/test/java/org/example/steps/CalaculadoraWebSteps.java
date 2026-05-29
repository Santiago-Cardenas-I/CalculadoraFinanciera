package org.example.steps;

import io.cucumber.java.en.*;


public class CalaculadoraWebSteps {

    @Given("el usuario abre la pagina de login de la calculadora")
    public void elUsuarioAbreLaPaginaDeLoginDeLaCalculadora() {
        // Tu lógica para abrir la página web aquí
    }

    @When("el usuario ingresa el correo {string} y la contrasena {string}")
    public void elUsuarioIngresaElCorreoYLaContrasena(String correo, String contrasena) {
        // Tu lógica para ingresar credenciales
    }

    @Then("el usuario deberia ver el titulo {string} en la aplicacion")
    public void elUsuarioDeberiaVerElTituloEnLaAplicacion(String tituloEsperado) {
        // Tu lógica para verificar el título
    }

    @When("el usuario ingresa un monto de {string}, un plazo de {string} y una tasa de {string}")
    public void elUsuarioIngresaUnMontoDeUnPlazoDeYUnaTasaDe(String monto, String plazo, String tasa) {
        // Tu lógica para llenar el simulador
    }

    @And("selecciona el tipo de calculo {string}")
    public void seleccionaElTipoDeCalculo(String tipoCalculo) {
        // Tu lógica para seleccionar el tipo
    }

    @And("hace clic en el boton {string}")
    public void haceClicEnElBoton(String boton) {
        // Tu lógica para hacer clic
    }

    @Then("el usuario deberia ver los resultados de la {string}")
    public void elUsuarioDeberiaVerLosResultadosDeLa(String resultadoEsperado) {
        // Tu lógica para validar los resultados finales
    }
}