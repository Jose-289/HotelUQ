package edu.hoteluq.modelo;

import java.time.LocalDateTime;

public class Reembolso {

    private  String id;
    private LocalDateTime fecha;
    private double valor;
    private String motivo;
    private boolean procesado;

    public Reembolso(String id, LocalDateTime fecha, double valor, String motivo, boolean procesado) {
        this.id = id;
        this.fecha = fecha;
        this.valor = valor;
        this.motivo = motivo;
        this.procesado = procesado;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getValor() {
        return valor;
    }

    public String getMotivo() {
        return motivo;
    }

    public boolean isProcesado() {
        return procesado;
    }

    public void procesar(){
        procesado = true;
    }
}
