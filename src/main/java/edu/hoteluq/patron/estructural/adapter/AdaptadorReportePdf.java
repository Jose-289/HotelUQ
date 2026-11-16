package edu.hoteluq.patron.estructural.adapter;

import edu.hoteluq.modelo.DatosReporte;
import edu.hoteluq.modelo.Reserva;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorReportePdf implements GeneradorReporte {

    private final MotorDocumentoPdf motorPdf = new MotorDocumentoPdf();

    @Override
    public void generarArchivo(DatosReporte datos, File archivoDestino) throws IOException {
        List<String> lineas = new ArrayList<>();
        lineas.add("Fecha de generacion: " + datos.getFechaFormateada());
        lineas.add("----------------------------------------------------------------------");
        lineas.add("RESUMEN OPERATIVO:");
        lineas.add(String.format("Habitaciones: %d totales (Disponibles: %d | Ocupadas: %d)",
                datos.getTotalHabitaciones(), datos.getHabitacionesDisponibles(), datos.getHabitacionesOcupadas()));
        lineas.add("Clientes Registrados: " + datos.getTotalClientes());
        lineas.add("Reservas Confirmadas: " + datos.getTotalReservasConfirmadas());
        lineas.add(String.format("Ingresos Totales: $%.2f", datos.getIngresosTotales()));
        lineas.add("----------------------------------------------------------------------");
        lineas.add("DETALLE DE RESERVAS:");

        if (datos.getReservas().isEmpty()) {
            lineas.add("No hay reservas registradas.");
        } else {
            for (Reserva r : datos.getReservas()) {
                lineas.add(String.format("%s | %s | Hab %s | $%.2f | %s",
                        r.getId(),
                        r.getCliente().getNombre(),
                        r.getHabitacion().getNumero(),
                        r.getValorTotal(),
                        r.getEstado().name()));
            }
        }

        motorPdf.renderizarPdf(archivoDestino, datos.getTitulo(), lineas);
    }

    @Override
    public String getExtension() {
        return "pdf";
    }

    @Override
    public String getDescripcionFormato() {
        return "Documento PDF (.pdf)";
    }
}