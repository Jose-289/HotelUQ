package edu.hoteluq.patron.comportamental.state;

public class NivelPlata implements EstadoFidelizacion {

    @Override
    public String getNombreNivel() {
        return "PLATA";
    }

    @Override
    public double getMultiplicadorPuntos() {
        return 1.25; // +25% de puntos por reserva
    }

    @Override
    public double getPorcentajeBonoCanje() {
        return 0.10; // +10% de valor al convertir puntos a saldo
    }

    @Override
    public void evaluarTransicion(ContextoFidelizacion contexto) {
        if (contexto.getPuntosAcumulados() >= 3000) {
            contexto.setEstado(new NivelOro());
        } else if (contexto.getPuntosAcumulados() < 1000) {
            contexto.setEstado(new NivelBronce());
        }
    }
}