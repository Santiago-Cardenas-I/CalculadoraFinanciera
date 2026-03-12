package org.example;

import org.example.calcularcuota.CalculadoraFinanciera;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) {


        try (Scanner sc = new Scanner(System.in)) {


            LOGGER.info("----- CALCULADORA FINANCIERA -----");

            LOGGER.info("Monto: ");
            double monto = sc.nextDouble();

            LOGGER.info("Tasa anual (%): ");
            double tasaAnual = sc.nextDouble();

            LOGGER.info("Meses: ");
            int meses = sc.nextInt();

            CalculadoraFinanciera calculadora = new CalculadoraFinanciera();

            try {
                calculadora.validarEntradas(monto, tasaAnual, meses);
            } catch (IllegalArgumentException e) {
                LOGGER.severe("Error: " + e.getMessage());
                return;
            }



            LOGGER.info("\n--- INTERES SIMPLE ---");

            double interesSimple = calculadora.calcularInteresSimple(monto, tasaAnual, meses);

            LOGGER.log(java.util.logging.Level.INFO,"Interes simple: {0}", interesSimple );
            double total = monto + interesSimple;
            LOGGER.log(java.util.logging.Level.INFO, "TOTAL: {0}", total);

            LOGGER.info("\n--- INTERES COMPUESTO ---");
            double totalCompuesto = calculadora.calcularInteresCompuesto(monto, tasaAnual, meses);
            double interesCompuesto = totalCompuesto - monto;
            LOGGER.log(java.util.logging.Level.INFO, "Interés: {0}", interesCompuesto);
            LOGGER.log(java.util.logging.Level.INFO, "TOTAL: {0}", totalCompuesto);

            LOGGER.info("\n--- CUOTA MENSUAL---");
            double cuota = calculadora.calcularCuotaMensual(monto, tasaAnual, meses);
            LOGGER.log(java.util.logging.Level.INFO, "Cuota: {0}", cuota);

            LOGGER.info("\n---- TABLA DE AMORTIZACION ---");
            calculadora.generarTabla(monto, tasaAnual, meses, cuota);


        }
    }
}