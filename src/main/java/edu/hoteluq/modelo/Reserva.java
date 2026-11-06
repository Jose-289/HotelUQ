package edu.hoteluq.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reserva {

    private String id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private double valorTotal;
    private EstadoReserva estado;
    private List<Servicio> servicios;

    public Reserva(String id, Cliente cliente, Habitacion habitacion, LocalDate fechaEntrada,
                   LocalDate fechaSalida, double valorTotal, EstadoReserva estado) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.valorTotal = valorTotal;
        this.estado = estado;
        this.servicios = new ArrayList<>();
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

    public List<Servicio> getServicios() {
        return servicios;
    }

    public long calcularNoches(){
        return 0;
    }

    public double calcularTotal(){
        return  valorTotal;
    }

    public void agregarServicio(Servicio servicio){
        servicios.add(servicio);
    }

    public void cancelar(){
        estado = EstadoReserva.CANCELADA;
    }

    public void confirmar(){
        estado = EstadoReserva.CONFIRMADA;
    }



}
