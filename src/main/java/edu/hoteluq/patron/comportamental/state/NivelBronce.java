package edu.hoteluq.patron.comportamental.state;

public class NivelBronce implements EstadoFidelizacion {

    @Override
    public String getNombreNivel() {
        return "BRONCE";
    }

    @Override
    public double getMultiplicadorPuntos() {
        return 1.0; // 1 punto por cada $1.000
    }

    @Override
    public double getPorcentajeBonoCanje() {
        return 0.0; // Sin bono extra al canjear
    }

    @Override
    public void evaluarTransicion(ContextoFidelizacion contexto) {
        if (contexto.getPuntosAcumulados() >= 3000) {
            contexto.setEstado(new NivelOro());
        } else if (contexto.getPuntosAcumulados() >= 1000) {
            contexto.setEstado(new NivelPlata());
        }
    }
}