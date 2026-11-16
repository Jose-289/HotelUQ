package edu.hoteluq.patron.estructural.adapter;

import edu.hoteluq.modelo.DatosReporte;
import edu.hoteluq.modelo.Reserva;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorReporteTxt implements GeneradorReporte {

    private final EscritorTextoPlano escritor = new EscritorTextoPlano();

    @Override
    public void generarArchivo(DatosReporte datos, File archivoDestino) throws IOException {
        List<String> lineas = new ArrayList<>();
        lineas.add("====================================================================");
        lineas.add("  " + datos.getTitulo().toUpperCase());
        lineas.add("  Generado: " + datos.getFechaFormateada());
        lineas.add("====================================================================");
        lineas.add("");
        lineas.add("RESUMEN EJECUTIVO:");
        lineas.add(String.format(" - Total Habitaciones:   %d (Disponibles: %d | Ocupadas: %d)",
                datos.getTotalHabitaciones(), datos.getHabitacionesDisponibles(), datos.getHabitacionesOcupadas()));
        lineas.add(String.format(" - Clientes Registrados: %d", datos.getTotalClientes()));
        lineas.add(String.format(" - Reservas Confirmadas: %d", datos.getTotalReservasConfirmadas()));
        lineas.add(String.format(" - Ingresos Totales:     $%.2f", datos.getIngresosTotales()));
        lineas.add("");
        lineas.add("DETALLE DE RESERVAS:");
        lineas.add("--------------------------------------------------------------------");

        if (datos.getReservas().isEmpty()) {
            lineas.add(" No hay reservas registradas en el sistema.");
        } else {
            for (Reserva r : datos.getReservas()) {
                lineas.add(String.format(" [%s] Cliente: %-18s | Hab: %-4s | Total: $%-10.2f | Estado: %s",
                        r.getId(),
                        r.getCliente().getNombre(),
                        r.getHabitacion().getNumero(),
                        r.getValorTotal(),
                        r.getEstado().name()));
            }
        }
        lineas.add("====================================================================");

        escritor.escribirLineasEnArchivo(archivoDestino, lineas);
    }

    @Override
    public String getExtension() {
        return "txt";
    }

    @Override
    public String getDescripcionFormato() {
        return "Texto Plano (.txt)";
    }
}