package edu.hoteluq.servicio;

public class ReembolsoCliente implements EstrategiaReembolso{

    @Override
    public double calcularReembolso(double valorPagado, long horasRestantes){

        if (horasRestantes > 72){
            return valorPagado;
        }

        if (horasRestantes >= 24){
            return valorPagado * 0.80;
        }

        return 0;
    }
}
