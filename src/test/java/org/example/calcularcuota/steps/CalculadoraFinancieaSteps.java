package org.example.calcularcuota.steps;

import io.cucumber.java.en.*;
import org.example.controller.CalculadoraController;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraFinancieaSteps {

    private final CalculadoraController controller = new CalculadoraController();

    private double monto;
    private double tasa;
    private int meses;
    private double resultadoCuota;
    private IllegalArgumentException ExceptionCapturada;

    @Given("que el usuario se encuentra en la calculadora financiera")
    public void queElUsuarioSeEncuentraEnLaCalculadoraFinanciera() {
        // Reiniciamos el estado para evitar falsos positivos entre escenarios
        ExceptionCapturada = null;
    }

    @When("el usuario ingresa un monto de {double}")
    public void elUsuarioIngresaUnMontoDe(double monto) {
        this.monto = monto;
    }

    @And("Una tasa de interes del {double}%")
    public void unaTasaDeInteresDel(double tasa) {
        this.tasa = tasa;
    }

    @And("un plazo de {int} meses")
    public void unPlazoDeMeses(int meses) {
        this.meses = meses;
        // Calculamos aquí porque es el último paso del ingreso de datos
        resultadoCuota = controller.cuota(monto, tasa, meses);
    }

    @Then("el resultado de la cuota mensual debe ser {double}")
    public void verificarElResultadoDeLaCuotaMensual(double cuotaEsperada) {
        assertEquals(cuotaEsperada, resultadoCuota, 0.01, "La cuota calculada no coincide con la esperada");
    }

    // --- ESCENARIO 2: ERROR AL INGRESAR MONTO ---

    @When("el usuario ingresa un monto de {double}, una tasa de interes del {double}% y un plazo de {int} meses")
    public void intentarCalcularConValorInvalido(double monto, double tasa, int meses) {
        try {
            controller.cuota(monto, tasa, meses);
        } catch (IllegalArgumentException e) {
            ExceptionCapturada = e;
        }
    }

    @Then("el sistema debe mostrar un mensaje de error indicando que el monto debe ser mayor a cero")
    public void verificarMensajeDeErrorMonto() {
        assertNotNull(ExceptionCapturada, "Se esperaba una excepción pero no se lanzó. Verifica que 'validarEntradas' se esté llamando.");
        assertEquals("El monto debe ser mayor que 0", ExceptionCapturada.getMessage());
    }

    // --- ESCENARIO 3: RECALCULAR ---

    @Given("el usuario calculo una cuota con un monto de {double}, una tasa de {double}% y un plazo de {int} meses")
    public void calcularCuotaInicial(double monto, double tasa, int meses) {
        resultadoCuota = controller.cuota(monto, tasa, meses);
        assertTrue(resultadoCuota > 0, "La cuota inicial calculada debe ser mayor a cero");
    }

    // Nota: Dejé los dos espacios después de "calculo" ("calculo  con") porque así está en tu Gherkin
    @When("el usuario realize un nuevo calculo  con un monto de {double}, una tasa de {double}% y un plazo de {int} meses")
    public void realizarNuevoCalculo(double monto, double tasa, int meses) {
        // En un nuevo cálculo, puede que no se lance error, pero reseteamos por seguridad
        ExceptionCapturada = null;
        try {
            resultadoCuota = controller.cuota(monto, tasa, meses);
        } catch (IllegalArgumentException e) {
            ExceptionCapturada = e;
        }
    }

    @Then("el sistema debe retornar la nueva cuota mensual sin errores")
    public void verificarNuevoCalculoSinErrores(){
        assertNull(ExceptionCapturada, "Hubo un error al recalcular: " + (ExceptionCapturada != null ? ExceptionCapturada.getMessage() : ""));
        assertTrue(resultadoCuota > 0, "La nueva cuota calculada debe ser mayor a cero");
    }
}