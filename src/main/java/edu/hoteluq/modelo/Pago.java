package edu.hoteluq.modelo;

import java.time.LocalDateTime;

public class Pago {

    private String id;
    private LocalDateTime fecha;
    private double valor;
    private EstadoPago estado;
    private Reserva reserva;
    private Reembolso reembolso;

    public Pago(String id, LocalDateTime fecha, double valor, EstadoPago estado, Reserva reserva) {
        this.id = id;
        this.fecha = fecha;
        this.valor = valor;
        this.estado = estado;
        this.reserva = reserva;
        this.reembolso = null;
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

    public Reserva getReserva() {
        return reserva;
    }

    public Reembolso getReembolso() {
        return reembolso;
    }

    public void asignarReembolso(Reembolso reembolso){
        this.reembolso = reembolso;
    }

    public void procesar(){
        TarjetaVirtual tarjeta = reserva.getCliente().getTarjetaVirtual();;

        if (tarjeta.puedePagar(valor)){
            aprobar();
            tarjeta.pagar(valor);

            MovimientoTarjeta movimiento = new MovimientoTarjeta(id,LocalDateTime.now(), TipoMovimiento.PAGO,
                    valor, "Pago de reserva");

            tarjeta.getMovimientos().add(movimiento);

            reserva.asignarPago(this);
        }else {
            rechazar();
        }

    }

    public void aprobar(){
        estado = EstadoPago.APROBADO;
    }

    public void rechazar(){
        estado = EstadoPago.RECHAZADO;
    }

    public void reembolsar(){
        estado = EstadoPago.REEMBOLSADO;
    }
}
