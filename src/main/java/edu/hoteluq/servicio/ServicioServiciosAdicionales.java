package edu.hoteluq.servicio;

import edu.hoteluq.modelo.Servicio;
import edu.hoteluq.repositorio.RepositorioServicios;
import java.util.List;

public class ServicioServiciosAdicionales {
    private static final ServicioServiciosAdicionales instancia = new ServicioServiciosAdicionales();
    private final RepositorioServicios repositorio = RepositorioServicios.getInstancia();

    private ServicioServiciosAdicionales() {}

    public static ServicioServiciosAdicionales getInstancia() {
        return instancia;
    }

    public List<Servicio> obtenerServiciosDisponibles() {
        return repositorio.obtenerTodos();
    }

    public void registrarServicio(String id, String nombre, String descripcion, double precio, boolean disponible) {
        if (repositorio.buscarPorId(id) != null) {
            throw new IllegalArgumentException("Ya existe un servicio con el código " + id);
        }
        Servicio servicio = new Servicio(id, nombre, descripcion, precio, disponible);
        repositorio.guardar(servicio);
    }

    public void eliminarServicio(Servicio servicio) {
        repositorio.eliminar(servicio);
    }
}