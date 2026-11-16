package edu.hoteluq.patron.estructural.proxy;

import java.util.List;

public interface OperacionBilletera {
    double consultarSaldo();
    boolean estaActiva();
    void recargarSaldo(double monto);
    List<String> obtenerHistorialAuditoria();
}