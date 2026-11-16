package edu.hoteluq.patron.comportamental.strategy;

import edu.hoteluq.modelo.Cotizable;

public class DescuentoPorcentajeStrategy implements EstrategiaDescuento {
    private final double porcentaje;
    private final int minimoNoches;

    public DescuentoPorcentajeStrategy(double porcentaje, int minimoNoches) {
        this.porcentaje = porcentaje;
        this.minimoNoches = minimoNoches;
    }

    @Override
    public boolean esAplicable(Cotizable cotizable) {
        return cotizable != null && cotizable.calcularNoches() >= minimoNoches;
    }

    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * (porcentaje / 100.0);
    }

    @Override
    public String getNombreEstrategia() {
        return "Porcentaje (" + porcentaje + "% - Mín. " + minimoNoches + " noches)";
    }
}