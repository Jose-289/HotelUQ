package edu.hoteluq.model;

import java.time.LocalDate;

public class Reserva {

    private String id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private double valorTotal;
    private EstadoReserva estado;

    public Reserva(String id, Cliente cliente, Habitacion habitacion, LocalDate fechaEntrada,
                   LocalDate fechaSalida, double valorTotal, EstadoReserva estado) {

        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.valorTotal = valorTotal;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void cambiarEstado(EstadoReserva estado){

        this.estado = estado;
    }
}
