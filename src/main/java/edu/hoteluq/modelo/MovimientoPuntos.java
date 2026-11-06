package edu.hoteluq.modelo;

import java.time.LocalDate;

public class MovimientoPuntos {

    private String id;
    private LocalDate fecha;
    private LocalDate fechaVencimiento;
    private TipoMovimientoPuntos tipo;
    private int puntos;
    private String descripcion;

    public MovimientoPuntos(String id, LocalDate fecha, LocalDate fechaVencimiento,
                            TipoMovimientoPuntos tipo, int puntos, String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.fechaVencimiento = fechaVencimiento;
        this.tipo = tipo;
        this.puntos = puntos;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public TipoMovimientoPuntos getTipo() {
        return tipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
