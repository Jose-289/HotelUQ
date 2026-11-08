package edu.hoteluq.modelo;

import java.time.LocalDate;

public class Promocion {

    private String id;
    private String nombre;
    private String descripcion;
    private double porcentajeDescuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int nochesMinimas;

    public Promocion(String id, String nombre, String descripcion,  double porcentajeDescuento,
                     LocalDate fechaInicio, LocalDate fechaFin, int nochesMinimas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.porcentajeDescuento = porcentajeDescuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nochesMinimas = nochesMinimas;
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

    public int getNochesMinimas() {
        return nochesMinimas;
    }

    public boolean esAplicable(Reserva reserva){
        LocalDate fechaReserva = reserva.getFechaEntrada().toLocalDate();
        int noches = (int) reserva.calcularNoches();

        return !fechaReserva.isBefore(fechaInicio)
                && !fechaReserva.isAfter(fechaFin)
                && noches >= nochesMinimas;
    }

    public double calcularDescuento(double valor){
        return valor * porcentajeDescuento / 100;
    }
}
