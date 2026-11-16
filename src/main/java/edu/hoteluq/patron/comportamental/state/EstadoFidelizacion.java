package edu.hoteluq.patron.comportamental.state;

public interface EstadoFidelizacion {
    String getNombreNivel();
    double getMultiplicadorPuntos();
    double getPorcentajeBonoCanje();
    void evaluarTransicion(ContextoFidelizacion contexto);
}