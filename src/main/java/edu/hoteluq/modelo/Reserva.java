package edu.hoteluq.modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Reserva {

    private String id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private double valorTotal;
    private EstadoReserva estado;
    private List<Servicio> servicios;
    private Promocion promocion;
    private Pago pago;

    public Reserva(String id, Cliente cliente, Habitacion habitacion, LocalDateTime fechaEntrada,
                   LocalDateTime fechaSalida, double valorTotal, EstadoReserva estado) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.valorTotal = valorTotal;
        this.estado = estado;
        this.servicios = new ArrayList<>();
        this.promocion = null;
        this.pago = null;
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

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
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

    public Promocion getPromocion() {
        return promocion;
    }

    public Pago getPago() {
        return pago;
    }

    public long calcularNoches(){
        return ChronoUnit.DAYS.between(
                fechaEntrada.toLocalDate(),
                fechaSalida.toLocalDate()
        );
    }

    public double calcularValorHabitacion(){
        return habitacion.getTipo().calcularValor((int) calcularNoches());
    }

    public double calcularValorServicios(){
        double totalServicios = 0;

        for (Servicio servicio : servicios){
            totalServicios += servicio.getPrecio();
        }
        return totalServicios;
    }

    public boolean fechasValidas(){
        return fechaSalida.isAfter(fechaEntrada);
    }

    public double calcularTotal(){
        double total = calcularValorHabitacion();

        total += calcularValorServicios();

        if (promocion !=  null && promocion.esAplicable(this)){
            total -= promocion.calcularDescuento(total);
        }

        return total;
    }

    public void agregarServicio(Servicio servicio){
        if (servicio.isDisponible()){
            servicios.add(servicio);
        }
    }

    public void aplicarPromocion(Promocion promocion){
        this.promocion = promocion;
    }

    public void asignarPago(Pago pago){
        this.pago = pago;
    }

    public void cancelar(){
        estado = EstadoReserva.CANCELADA;
    }

    public void confirmar(){
        estado = EstadoReserva.CONFIRMADA;
    }



}
