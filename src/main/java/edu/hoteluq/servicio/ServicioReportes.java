package edu.hoteluq.servicio;

import edu.hoteluq.modelo.Reserva;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ServicioReportes {

    private final ServicioReservas servicioReservas;

    public ServicioReportes(){
        this.servicioReservas = ServicioReservas.getInstancia();
    }

    public List<Reserva> obtenerReservas(){
        return servicioReservas.obtenerTodasReservas();
    }

    public String generarReporteReservas(){

        StringBuilder reporte = new StringBuilder();

        reporte.append("========================================\n");
        reporte.append("          REPORTE DE RESERVAS\n");
        reporte.append("               HotelUQ\n");
        reporte.append("========================================\n\n");

        List<Reserva> reservas = obtenerReservas();

        if (reservas.isEmpty()){
            reporte.append("No existen reservas registradas.\n");
            return reporte.toString();
        }

        for (Reserva reserva : reservas){

            reporte.append("Reserva: ")
                    .append(reserva.getId())
                    .append("\n");

            reporte.append("Cliente: ")
                    .append(reserva.getCliente().getNombre())
                    .append("\n");

            reporte.append("Habitación: ")
                    .append(reserva.getHabitacion().getNumero())
                    .append("\n");

            reporte.append("Entrada: ")
                    .append(reserva.getFechaEntrada())
                    .append("\n");

            reporte.append("Salida: ")
                    .append(reserva.getFechaSalida())
                    .append("\n");

            reporte.append("Estado: ")
                    .append(reserva.getEstado())
                    .append("\n");

            reporte.append("Valor total: $")
                    .append(reserva.getValorTotal())
                    .append("\n");

            reporte.append("----------------------------------------\n");
        }

        return reporte.toString();
    }

    public File guardarReporteTXT() throws IOException{

        String contenido = generarReporteReservas();

        File archivo = new File("reporte_reservas.txt");

        try (FileWriter escritor = new FileWriter(archivo)) {
            escritor.write(contenido);
        }

        return archivo;
    }

}
