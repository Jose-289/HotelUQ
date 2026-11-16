package edu.hoteluq.patron.estructural.decorator;

import edu.hoteluq.modelo.Cotizable;

public class ReservaBaseDecorator implements CostoReserva {
    private final Cotizable cotizable;

    public ReservaBaseDecorator(Cotizable cotizable) {
        this.cotizable = cotizable;
    }

    @Override
    public double calcularCosto() {
        return cotizable.calcularValorHabitacion();
    }

    @Override
    public String getDescripcion() {
        return "Habitación " + cotizable.getHabitacion().getNumero()
                + " (" + cotizable.calcularNoches() + " noches)";
    }
}