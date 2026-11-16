package edu.hoteluq.patron.comportamental.strategy;

import edu.hoteluq.modelo.Reserva;

public interface EstrategiaDescuento {
    boolean esAplicable(Reserva reserva);
    double calcularDescuento(double subtotal);
    String getNombreEstrategia();
}
