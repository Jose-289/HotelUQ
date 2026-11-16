package edu.hoteluq.patron.comportamental.observer;

import edu.hoteluq.modelo.Reserva;
import java.time.LocalDateTime;

public class EventoHotel {

    public enum TipoEvento {
        RESERVA_CONFIRMADA,
        RESERVA_CANCELADA,
        RECARGA_BILLETERA,
        CANJE_PUNTOS
    }

    private final TipoEvento tipo;
    private final String clienteId;
    private final String descripcion;
    private final double monto;
    private final LocalDateTime fechaHora;

    public EventoHotel(TipoEvento tipo, String clienteId, String descripcion, double monto) {
        this.tipo = tipo;
        this.clienteId = clienteId;
        this.descripcion = descripcion;
        this.monto = monto;
        this.fechaHora = LocalDateTime.now();
    }

    public TipoEvento getTipo() { return tipo; }
    public String getClienteId() { return clienteId; }
    public String getDescripcion() { return descripcion; }
    public double getMonto() { return monto; }
    public LocalDateTime getFechaHora() { return fechaHora; }
}