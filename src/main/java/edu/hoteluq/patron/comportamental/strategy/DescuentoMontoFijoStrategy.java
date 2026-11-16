package edu.hoteluq.patron.comportamental.strategy;

import edu.hoteluq.modelo.Cotizable;

public class DescuentoMontoFijoStrategy implements EstrategiaDescuento {
    private final double montoDescuento;
    private final double montoMinimoCompra;

    public DescuentoMontoFijoStrategy(double montoDescuento, double montoMinimoCompra) {
        this.montoDescuento = montoDescuento;
        this.montoMinimoCompra = montoMinimoCompra;
    }

    @Override
    public boolean esAplicable(Cotizable cotizable) {
        if (cotizable == null) return false;
        double base = cotizable.calcularValorHabitacion() + cotizable.calcularValorServicios();
        return base >= montoMinimoCompra;
    }

    @Override
    public double calcularDescuento(double subtotal) {
        return Math.min(subtotal, montoDescuento);
    }

    @Override
    public String getNombreEstrategia() {
        return "Monto Fijo ($" + montoDescuento + " - Compra mín. $" + montoMinimoCompra + ")";
    }
}