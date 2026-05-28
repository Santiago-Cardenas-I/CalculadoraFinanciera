package org.example.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SimuladorPage extends PageObject {

    @FindBy(tagName = "h2")
    WebElement tituloPrincipal;

    // Asumiendo los selectores de tu calculadora. ¡Ajusta los IDs si son diferentes!
    @FindBy(name = "monto")
    WebElement montoField;

    @FindBy(name = "plazo")
    WebElement plazoField;

    @FindBy(name = "tasa")
    WebElement tasaField;

    @FindBy(tagName = "select")
    WebElement tipoCalculoSelect;

    @FindBy(xpath = "//button[contains(text(),'Calcular')]")
    WebElement calcularButton;

    @FindBy(xpath = "//h3[contains(text(),'Cuota Fija Mensual')]")
    WebElement tituloResultado;

    public String obtenerTextoTitulo() {
        return tituloPrincipal.getText();
    }

    public void llenarFormularioSimulacion(String monto, String plazo, String tasa) {
        montoField.sendKeys(monto);
        plazoField.sendKeys(plazo);
        tasaField.sendKeys(tasa);
    }

    public void seleccionarTipoCalculo(String tipo) {
        Select dropdown = new Select(tipoCalculoSelect);
        dropdown.selectByValue(tipo);
    }

    public void hacerClicEnCalcular() {
        calcularButton.click();
    }

    public boolean esResultadoVisible() {
        return tituloResultado.isDisplayed();
    }
}
