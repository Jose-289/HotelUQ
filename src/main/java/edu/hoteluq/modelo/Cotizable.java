package edu.hoteluq.modelo;

import java.time.LocalDateTime;
import java.util.List;

public interface Cotizable {
    Habitacion getHabitacion();
    LocalDateTime getFechaEntrada();
    LocalDateTime getFechaSalida();
    List<Servicio> getServicios();
    long calcularNoches();
    double calcularValorHabitacion();
    double calcularValorServicios();
}