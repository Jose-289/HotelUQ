package edu.hoteluq.modelo;

import edu.hoteluq.patron.comportamental.strategy.EstrategiaDescuento;
import java.time.LocalDate;

public class Promocion {
    private final String codigo;
    private final String descripcion;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final EstrategiaDescuento estrategia;
    private boolean activa;

    public Promocion(String codigo, String descripcion, LocalDate fechaInicio, LocalDate fechaFin,
                     EstrategiaDescuento estrategia, boolean activa) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estrategia = estrategia;
        this.activa = activa;
    }

    public String getCodigo() { return codigo; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public EstrategiaDescuento getEstrategia() { return estrategia; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return activa && !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
    }

    public boolean esAplicable(Reserva reserva) {
        return estaVigente() && estrategia != null && estrategia.esAplicable(reserva);
    }

    public double calcularDescuento(double subtotal) {
        return estrategia != null ? estrategia.calcularDescuento(subtotal) : 0.0;
    }
}