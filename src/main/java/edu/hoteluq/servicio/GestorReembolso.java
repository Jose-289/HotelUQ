package edu.hoteluq.servicio;

public class GestorReembolso {

    private EstrategiaReembolso estrategia;

    public GestorReembolso(EstrategiaReembolso estrategia){
        this.estrategia = estrategia;
    }

    public double calcularReembolso(double valorPagado, long horasRestantes){
        return estrategia.calcularReembolso(valorPagado, horasRestantes);
    }
}
