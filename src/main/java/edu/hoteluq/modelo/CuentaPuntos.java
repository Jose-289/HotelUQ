package edu.hoteluq.modelo;

import java.util.ArrayList;
import java.util.List;

public class CuentaPuntos {

    private int puntosAcumulados;
    private List<MovimientoPuntos> movimientos;

    public CuentaPuntos(int puntosAcumulados){
        this.puntosAcumulados = puntosAcumulados;
        this.movimientos = new ArrayList<>();
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public List<MovimientoPuntos> getMovimientos() {
        return movimientos;
    }

    public void acumularPuntos(int puntos){
        puntosAcumulados += puntos;
    }

    public boolean redimirPuntos(int puntos){
        if (puntos <= puntosAcumulados){
            puntosAcumulados -= puntos;
            return true;
        }
        return false;
    }

    public void agregarMovimiento(MovimientoPuntos movimiento){
        movimientos.add(movimiento);
    }
}
