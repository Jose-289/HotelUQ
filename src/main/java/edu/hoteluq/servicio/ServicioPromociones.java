package edu.hoteluq.servicio;

import edu.hoteluq.modelo.Promocion;
import edu.hoteluq.patron.comportamental.strategy.EstrategiaDescuento;
import edu.hoteluq.repositorio.RepositorioPromociones;

import java.time.LocalDate;
import java.util.List;

public class ServicioPromociones {
    private static final ServicioPromociones instancia = new ServicioPromociones();
    private final RepositorioPromociones repositorio = RepositorioPromociones.getInstancia();

    private ServicioPromociones() {}

    public static ServicioPromociones getInstancia() {
        return instancia;
    }

    public List<Promocion> obtenerPromociones() {
        return repositorio.obtenerTodas();
    }

    public void registrarPromocion(String codigo, String descripcion, LocalDate inicio, LocalDate fin,
                                   EstrategiaDescuento estrategia, boolean activa) {
        if (repositorio.buscarPorCodigo(codigo) != null) {
            throw new IllegalArgumentException("Ya existe una promoción con el código " + codigo);
        }
        if (fin.isBefore(inicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio.");
        }
        Promocion promocion = new Promocion(codigo, descripcion, inicio, fin, estrategia, activa);
        repositorio.guardar(promocion);
    }

    public void eliminarPromocion(Promocion promocion) {
        repositorio.eliminar(promocion);
    }
}