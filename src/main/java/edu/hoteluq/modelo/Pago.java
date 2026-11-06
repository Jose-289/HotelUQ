package edu.hoteluq.modelo;

import java.time.LocalDateTime;

public class Pago {

    private String id;
    private LocalDateTime fecha;
    private double valor;
    private EstadoPago estado;

    public Pago(String id, LocalDateTime fecha, double valor, EstadoPago estado) {
        this.id = id;
        this.fecha = fecha;
        this.valor = valor;
        this.estado = estado;
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

    public EstadoPago getEstado() {
        return estado;
    }

    public void procesar(){


    }

    public void aprobar(){
        estado = EstadoPago.APROBADO;
    }

    public void rechazar(){
        estado = EstadoPago.RECHAZADO;
    }
}
