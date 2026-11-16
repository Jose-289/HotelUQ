package edu.hoteluq.servicio;

import edu.hoteluq.modelo.*;
import edu.hoteluq.patron.estructural.adapter.GeneradorReporte;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ServicioReportes {

    private static final ServicioReportes instancia = new ServicioReportes();

    private final ServicioHabitaciones servicioHabitaciones = ServicioHabitaciones.getInstancia();
    private final ServicioUsuarios servicioUsuarios = ServicioUsuarios.getInstancia();
    private final ServicioReservas servicioReservas = ServicioReservas.getInstancia();

    private ServicioReportes() {}

    public static ServicioReportes getInstancia() {
        return instancia;
    }

    public DatosReporte recopilarDatosActuales() {
        List<Habitacion> habitaciones = servicioHabitaciones.obtenerTodasHabitaciones();
        int disponibles = (int) habitaciones.stream()
                .filter(h -> h.getEstado() == EstadoHabitacion.DISPONIBLE).count();
        int ocupadas = (int) habitaciones.stream()
                .filter(h -> h.getEstado() == EstadoHabitacion.OCUPADA).count();

        int clientes = (int) servicioUsuarios.obtenerUsuarios().stream()
                .filter(u -> u instanceof Cliente).count();

        List<Reserva> reservas = servicioReservas.obtenerTodasReservas();
        int confirmadas = (int) reservas.stream()
                .filter(r -> r.getEstado() == EstadoReserva.CONFIRMADA).count();
        double ingresos = reservas.stream()
                .filter(r -> r.getEstado() == EstadoReserva.CONFIRMADA)
                .mapToDouble(Reserva::getValorTotal)
                .sum();

        return new DatosReporte(
                "Reporte General de Operaciones - HotelUQ",
                habitaciones.size(),
                disponibles,
                ocupadas,
                clientes,
                confirmadas,
                ingresos,
                reservas
        );
    }

    public void exportarReporte(GeneradorReporte adaptador, File archivoDestino) throws IOException {
        if (adaptador == null) {
            throw new IllegalArgumentException("Debe seleccionar un formato de exportación.");
        }
        if (archivoDestino == null) {
            throw new IllegalArgumentException("Debe indicar una ruta válida para el archivo.");
        }
        DatosReporte datos = recopilarDatosActuales();
        adaptador.generarArchivo(datos, archivoDestino);
    }
}
