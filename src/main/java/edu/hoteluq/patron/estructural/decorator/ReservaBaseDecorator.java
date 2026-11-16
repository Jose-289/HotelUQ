package edu.hoteluq.patron.estructural.decorator;

import edu.hoteluq.modelo.Reserva;

public class ReservaBaseDecorator implements CostoReserva {
    private final Reserva reserva;

    public ReservaBaseDecorator(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public double calcularCosto() {
        return reserva.calcularValorHabitacion();
    }

    @Override
    public String getDescripcion() {
        return "Habitación " + reserva.getHabitacion().getNumero()
                + " (" + reserva.calcularNoches() + " noches)";
    }
}