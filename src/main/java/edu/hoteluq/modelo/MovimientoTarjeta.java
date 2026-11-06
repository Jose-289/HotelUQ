package edu.hoteluq.modelo;

import java.time.LocalDateTime;

public class MovimientoTarjeta {

    private String id;
    private LocalDateTime fecha;
    private TipoMovimiento tipo;
    private double valor;
    private String descripcion;

    public MovimientoTarjeta(String id, LocalDateTime fecha, TipoMovimiento tipo, double valor,
                             String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
