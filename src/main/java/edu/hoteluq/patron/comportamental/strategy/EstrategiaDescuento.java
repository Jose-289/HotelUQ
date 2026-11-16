package edu.hoteluq.patron.comportamental.strategy;

import edu.hoteluq.modelo.Cotizable;

public interface EstrategiaDescuento {
    boolean esAplicable(Cotizable cotizable);
    double calcularDescuento(double subtotal);
    String getNombreEstrategia();
}
