package org.example.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;

public class LoginPage extends PageObject {
    @FindBy(id = "email") // Basado en tu Login.jsx
    WebElement correoField;

    @FindBy(id = "password") // Basado en tu Login.jsx
    WebElement contrasenaField;

    @FindBy(className = "login-btn") // Basado en tu Login.jsx
    WebElement loginButton;

    public void abrirPagina() {
        openAt("http://localhost:5173"); // Puerto de tu frontend Vite
    }

    public void ingresarCredenciales(String correo, String contrasena) {
        correoField.sendKeys(correo);
        contrasenaField.sendKeys(contrasena);
        loginButton.click();
    }
}