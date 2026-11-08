package edu.hoteluq.servicio;

public interface EstrategiaReembolso {

    double calcularReembolso(double valorPagado, long horasRestantes);
}
