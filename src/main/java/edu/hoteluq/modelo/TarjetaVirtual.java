package edu.hoteluq.modelo;

import java.util.ArrayList;
import java.util.List;

public class TarjetaVirtual {

    private String numero;
    private double saldo;
    private boolean activa;
    private List<MovimientoTarjeta> movimientos;

    public TarjetaVirtual(String numero, double saldo, boolean activa) {
        this.numero = numero;
        this.saldo = saldo;
        this.activa = activa;
        this.movimientos = new ArrayList<>();
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean isActiva() {
        return activa;
    }

    public List<MovimientoTarjeta> getMovimientos() {
        return movimientos;
    }

    public boolean puedePagar(double valor){
        return activa && saldo >= valor;
    }

    public void pagar(double valor){
        saldo -= valor;
    }

    public void recargar(double valor){
        saldo += valor;
    }

    public void reembolsar(double valor){
        saldo += valor;
    }

    public double consultarSaldo(){
        return saldo;
    }
}
