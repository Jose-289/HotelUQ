package edu.hoteluq.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DatosReporte {
    private final String titulo;
    private final LocalDateTime fechaGeneracion;
    private final int totalHabitaciones;
    private final int habitacionesDisponibles;
    private final int habitacionesOcupadas;
    private final int totalClientes;
    private final int totalReservasConfirmadas;
    private final double ingresosTotales;
    private final List<Reserva> reservas;

    public DatosReporte(String titulo, int totalHabitaciones, int habitacionesDisponibles,
                        int habitacionesOcupadas, int totalClientes, int totalReservasConfirmadas,
                        double ingresosTotales, List<Reserva> reservas) {
        this.titulo = titulo;
        this.fechaGeneracion = LocalDateTime.now();
        this.totalHabitaciones = totalHabitaciones;
        this.habitacionesDisponibles = habitacionesDisponibles;
        this.habitacionesOcupadas = habitacionesOcupadas;
        this.totalClientes = totalClientes;
        this.totalReservasConfirmadas = totalReservasConfirmadas;
        this.ingresosTotales = ingresosTotales;
        this.reservas = List.copyOf(reservas);
    }

    public String getTitulo() { return titulo; }
    public String getFechaFormateada() {
        return fechaGeneracion.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public int getTotalHabitaciones() { return totalHabitaciones; }
    public int getHabitacionesDisponibles() { return habitacionesDisponibles; }
    public int getHabitacionesOcupadas() { return habitacionesOcupadas; }
    public int getTotalClientes() { return totalClientes; }
    public int getTotalReservasConfirmadas() { return totalReservasConfirmadas; }
    public double getIngresosTotales() { return ingresosTotales; }
    public List<Reserva> getReservas() { return reservas; }
}