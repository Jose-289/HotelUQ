package edu.hoteluq.patron.comportamental.state;

public class NivelOro implements EstadoFidelizacion {

    @Override
    public String getNombreNivel() {
        return "ORO";
    }

    @Override
    public double getMultiplicadorPuntos() {
        return 1.5; // +50% de puntos por reserva
    }

    @Override
    public double getPorcentajeBonoCanje() {
        return 0.20; // +20% de valor al convertir puntos a saldo
    }

    @Override
    public void evaluarTransicion(ContextoFidelizacion contexto) {
        if (contexto.getPuntosAcumulados() < 1000) {
            contexto.setEstado(new NivelBronce());
        } else if (contexto.getPuntosAcumulados() < 3000) {
            contexto.setEstado(new NivelPlata());
        }
    }
}