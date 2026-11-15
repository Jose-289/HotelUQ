package edu.hoteluq.servicio;

import edu.hoteluq.modelo.EstadoHabitacion;
import edu.hoteluq.modelo.Habitacion;
import edu.hoteluq.patron.creacional.factory.*;
import edu.hoteluq.repositorio.RepositorioHabitaciones;

import java.util.ArrayList;
import java.util.List;

public class ServicioHabitaciones {

    private static final ServicioHabitaciones instancia = new ServicioHabitaciones();
    private final RepositorioHabitaciones repositorioHabitaciones = RepositorioHabitaciones.getInstancia();
    private final List<FabricaHabitacion> fabricasDisponibles = new ArrayList<>();

    private ServicioHabitaciones() {
        // Registro inicial de fábricas (extensible en tiempo de ejecución)
        registrarFabrica(new FabricaHabitacionEstandar());
        registrarFabrica(new FabricaHabitacionFamiliar());
        registrarFabrica(new FabricaHabitacionSuite());
    }

    public static ServicioHabitaciones getInstancia() {
        return instancia;
    }

    /**
     * Permite registrar nuevas fábricas sin modificar el código interno del servicio (OCP).
     */
    public void registrarFabrica(FabricaHabitacion fabrica) {
        if (fabrica != null) {
            fabricasDisponibles.add(fabrica);
        }
    }

    public List<FabricaHabitacion> obtenerFabricasDisponibles() {
        return List.copyOf(fabricasDisponibles);
    }

    public List<Habitacion> obtenerTodasHabitaciones() {
        return repositorioHabitaciones.obtenerTodas();
    }

    /**
     * Registra una habitación delegando la instanciación polimórficamente a la fábrica recibida.
     */
    public void registrarHabitacion(String numero, FabricaHabitacion fabrica, EstadoHabitacion estado) {
        if (fabrica == null) {
            throw new IllegalArgumentException("Debe seleccionar una fábrica de habitación válida.");
        }
        if (repositorioHabitaciones.buscarPorNumero(numero) != null) {
            throw new RuntimeException("Ya existe una habitación registrada con el número " + numero);
        }

        Habitacion nuevaHabitacion = fabrica.crearHabitacion(numero, estado);
        repositorioHabitaciones.guardar(nuevaHabitacion);
    }

    public void eliminarHabitacion(Habitacion habitacion) {
        repositorioHabitaciones.eliminar(habitacion);
    }

    public long contarHabitacionesDisponibles() {
        return repositorioHabitaciones.obtenerTodas().stream()
                .filter(Habitacion::estaDisponible)
                .count();
    }

    public double calcularPorcentajeOcupacion() {
        List<Habitacion> total = repositorioHabitaciones.obtenerTodas();
        if (total.isEmpty()) return 0.0;
        long ocupadas = total.stream().filter(h -> h.getEstado() == EstadoHabitacion.OCUPADA).count();
        return (double) ocupadas / total.size() * 100.0;
    }
}