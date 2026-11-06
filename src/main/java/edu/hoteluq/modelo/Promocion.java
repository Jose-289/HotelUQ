package edu.hoteluq.modelo;

import java.time.LocalDate;

public class Promocion {

    private String id;
    private String nombre;
    private String descripcion;
    private double porcentajeDescuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Promocion(String id, String nombre, String descripcion,  double porcentajeDescuento,
                     LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.porcentajeDescuento = porcentajeDescuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public boolean esAplicable(Reserva reserva){
        return true;
    }

    public double calcularDescuento(double valor){
        return valor * porcentajeDescuento / 100;
    }
}
