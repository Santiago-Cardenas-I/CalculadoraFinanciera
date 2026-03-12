package org.example.calcularcuota;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraFinancieraTest {

    CalculadoraFinanciera calc = new CalculadoraFinanciera();

    @Test
    void testInteresSimple(){


        double interesSimple = calc.calcularInteresSimple(1000, 12, 24);
        assertEquals(240, interesSimple, 0.01);

    }
    @Test
    void testInteresSimplesUnMes(){
        double interesSimple = calc.calcularInteresSimple(1000, 12, 1);
        assertEquals(10, interesSimple, 0.01);
    }

    @Test
    void testInteresCompuesto(){


        double total = calc.calcularInteresCompuesto(1000, 12, 24);
        assertEquals(1269.73, total, 0.01);
    }
    @Test
    void testInteresCompuestoUnMes(){
        double total = calc.calcularInteresCompuesto(1000, 12, 1);
        assertEquals(1010, total, 0.01);
    }

    @Test
    void testInteresCompuestoMesesCero(){
        double total = calc.calcularInteresCompuesto(1000, 12, 0);
        assertEquals(1000, total, 0.01);
    }

    @Test
    void testCuotaMensual(){
        double cuota = calc.calcularCuotaMensual(1000000, 12, 24);
        assertEquals(47073.47, cuota, 0.01);
    }

    @Test
    void testCuotaUnMes(){
        double cuota = calc.calcularCuotaMensual(1000, 12, 1);
        assertTrue(cuota > 0);
    }

    @Test
    void testCuotaPocosMeses(){
        double cuota = calc.calcularCuotaMensual(5000, 10, 6);
        assertTrue(cuota > 0);
    }

    @Test
    void testMontoNegativo(){


        assertThrows(IllegalArgumentException.class, () -> {
            calc.validarEntradas(-1000, 11, 12);
        });
    }
    @Test
    void testMontoCero(){
        assertThrows(IllegalArgumentException.class, () -> {
            calc.validarEntradas(0, 10, 12);
        });
    }
    @Test
    void testCuotaMontoPequeno(){
        double cuota = calc.calcularCuotaMensual(100, 12, 12);
        assertTrue(cuota > 0);
    }

    @Test
    void testTasaNegativa(){



        assertThrows(IllegalArgumentException.class, () -> {
            calc.validarEntradas(1000, -5, 12);
        });
    }
    @Test
    void testTasaCero(){
        double cuota = calc.calcularCuotaMensual(1000, 0, 10);
        assertEquals(100, cuota, 0.01);
    }

    @Test
    void testTasaPequena(){
        double cuota = calc.calcularCuotaMensual(1000, 1, 12);
        assertTrue(cuota > 0);
    }

    @Test
    void testMesesInvalidos(){


        assertThrows(IllegalArgumentException.class, () -> {
            calc.validarEntradas(1000, 11, -12);
        });
    }
    @Test
    void testUnMes(){
        double interes = calc.calcularInteresSimple(1000, 12, 1);
        assertEquals(10, interes, 0.01);
    }

    @Test
    void testGenerarTabla(){

        double cuota = calc.calcularCuotaMensual(1000, 12, 12);

        var tabla = calc.generarTabla(1000, 12, 12, cuota);

        assertNotNull(tabla);
        assertEquals(12, tabla.size());
    }

    @Test
    void testTablaSaldoFinal(){

        double cuota = calc.calcularCuotaMensual(1000, 12, 12);

        var tabla = calc.generarTabla(1000, 12, 12, cuota);

        assertTrue(tabla.get(tabla.size() - 1).contains("Saldo"));
    }

    @Test
    void testSaldoNuncaNegativo(){
        double cuota = calc.calcularCuotaMensual(1000, 12, 1);
        calc.generarTabla(1000, 12, 1, cuota);
        assertTrue(cuota > 0);
    }








}
