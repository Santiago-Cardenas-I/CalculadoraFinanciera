package org.example.controller;

import org.example.calcularcuota.CalculadoraFinanciera;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finanzas")

public class CalculadoraController {

    private final CalculadoraFinanciera calculadora = new CalculadoraFinanciera();

    @GetMapping("/interes-simple")
    public double interesSimple(
            @RequestParam double monto,
            @RequestParam double tasa,
            @RequestParam int meses) {

        return calculadora.calcularInteresSimple(monto, tasa, meses);
    }

    @GetMapping("/interes-compuesto")
    public double interesCompuesto(
            @RequestParam double monto,
            @RequestParam double tasa,
            @RequestParam int meses) {

        return calculadora.calcularInteresCompuesto(monto, tasa, meses);
    }

    @GetMapping("/cuota")
    public double cuota(
            @RequestParam double monto,
            @RequestParam double tasa,
            @RequestParam int meses) {

        return calculadora.calcularCuotaMensual(monto, tasa, meses);
    }

}
