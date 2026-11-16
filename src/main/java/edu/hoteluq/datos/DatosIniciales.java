package edu.hoteluq.datos;

import edu.hoteluq.modelo.*;
import edu.hoteluq.patron.creacional.factory.*;
import edu.hoteluq.repositorio.RepositorioHabitaciones;
import edu.hoteluq.servicio.ServicioUsuarios;

public class DatosIniciales {

    public static void cargarUsuariosIniciales(){
        ServicioUsuarios servicioUsuarios = ServicioUsuarios.getInstancia();
        Administrador administrador = new Administrador(
                "ADM-001",
                "Administrador HotelUQ",
                "admin@hoteluq.com",
                "3156209678",
                "admin123"
        );
        servicioUsuarios.agregarUsuario(administrador);
    }

    public static void cargarHabitacionesIniciales(){
        RepositorioHabitaciones repo = RepositorioHabitaciones.getInstancia();

        FabricaHabitacion fabricaEstandar = new FabricaHabitacionEstandar();
        FabricaHabitacion fabricaFamiliar = new FabricaHabitacionFamiliar();
        FabricaHabitacion fabricaSuite = new FabricaHabitacionSuite();

        repo.guardar(fabricaEstandar.crearHabitacion("101", EstadoHabitacion.DISPONIBLE));
        repo.guardar(fabricaEstandar.crearHabitacion("102", EstadoHabitacion.DISPONIBLE));
        repo.guardar(fabricaFamiliar.crearHabitacion("201", EstadoHabitacion.DISPONIBLE));
        repo.guardar(fabricaFamiliar.crearHabitacion("202", EstadoHabitacion.DISPONIBLE));
        repo.guardar(fabricaSuite.crearHabitacion("301", EstadoHabitacion.DISPONIBLE));
    }
}
