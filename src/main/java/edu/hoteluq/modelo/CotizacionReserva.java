package edu.hoteluq.modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

public class CotizacionReserva implements Cotizable {

    private final Habitacion habitacion;
    private final LocalDateTime fechaEntrada;
    private final LocalDateTime fechaSalida;
    private final List<Servicio> servicios;

    public CotizacionReserva(Habitacion habitacion, LocalDateTime fechaEntrada,
                             LocalDateTime fechaSalida, List<Servicio> servicios) {
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.servicios = servicios != null ? List.copyOf(servicios) : Collections.emptyList();
    }

    @Override
    public Habitacion getHabitacion() { return habitacion; }

    @Override
    public LocalDateTime getFechaEntrada() { return fechaEntrada; }

    @Override
    public LocalDateTime getFechaSalida() { return fechaSalida; }

    @Override
    public List<Servicio> getServicios() { return servicios; }

    @Override
    public long calcularNoches() {
        long dias = ChronoUnit.DAYS.between(fechaEntrada.toLocalDate(), fechaSalida.toLocalDate());
        return Math.max(1, dias);
    }

    @Override
    public double calcularValorHabitacion() {
        return habitacion.calcularValor((int) calcularNoches());
    }

    @Override
    public double calcularValorServicios() {
        return servicios.stream().mapToDouble(Servicio::getPrecio).sum();
    }
}