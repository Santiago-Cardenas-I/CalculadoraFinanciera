package org.example.calcularcuota;

import java.util.List;
import java.util.logging.Logger;

public final class CalculadoraFinanciera {

    public void validarEntradas(double monto, double tasa, int meses) {

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0");
        }

        if (tasa < 0) {
            throw new IllegalArgumentException("La tasa no puede ser negativa");
        }

        if (meses <= 0) {
            throw new IllegalArgumentException("Los meses deben ser mayores que 0");
        }
    }

    private static final Logger LOGGER= Logger.getLogger(CalculadoraFinanciera.class.getName());

    private  static final int MESES_POR_ANIO = 12;
    private static final int PORCENTAJE = 100;

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    private double calcularTasaMensual(double tasaAnual) {
        return (tasaAnual / PORCENTAJE) / MESES_POR_ANIO;
    }

    public double calcularInteresSimple(double monto, double tasa, int meses) {

        double tasaMensual = calcularTasaMensual(tasa);
        return redondear(monto * tasaMensual * meses);
    }

    public double calcularInteresCompuesto(double monto, double tasa, int meses) {

        double tasaMensual = calcularTasaMensual(tasa);
        return redondear(monto * Math.pow((1 + tasaMensual), meses));
    }

    public double calcularCuotaMensual(double monto, double tasa, int meses) {

        validarEntradas(monto, tasa, meses);

        double tasaMensual = calcularTasaMensual(tasa);

        if (tasaMensual == 0) {
            return monto / meses;
        }

        return redondear
                (monto *
                        (tasaMensual * Math.pow(1 + tasaMensual, meses)) /
                        (Math.pow(1 + tasaMensual, meses) - 1)
                );

    }

    public List<String> generarTabla(double monto, double tasa, int meses, double cuota) {

        double tasaMensual = calcularTasaMensual(tasa);
        double saldo = monto;

        List<String> tabla = new java.util.ArrayList<>();

        for (int i = 1; i <= meses; i++) {

            double interes = saldo * tasaMensual;
            double capital = cuota - interes;
            saldo -= capital;

            if (saldo < 0) {
                saldo = 0;
            }

            String fila = String.format(
                    "Mes %d | Cuota %.2f | Interes %.2f | Capital %.2f | Saldo %.2f",
                    i, cuota, interes, capital, saldo
            );

            tabla.add(fila);

            if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
                LOGGER.info(fila);
            }
        }

        return tabla;

    }

}
