package edu.hoteluq.servicio;

public class ReembolsoHotel implements EstrategiaReembolso{

    @Override
    public double calcularReembolso(double valorPagado, long horasRestantes){

        return valorPagado;
    }
}
