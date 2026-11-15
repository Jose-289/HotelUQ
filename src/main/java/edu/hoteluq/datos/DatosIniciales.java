package edu.hoteluq.datos;

import edu.hoteluq.modelo.Administrador;
import edu.hoteluq.modelo.EstadoHabitacion;
import edu.hoteluq.modelo.Habitacion;
import edu.hoteluq.modelo.TipoHabitacion;
import edu.hoteluq.repositorio.RepositorioHabitaciones;
import edu.hoteluq.repositorio.RepositorioTiposHabitacion;
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

        RepositorioTiposHabitacion repositorioTipos = RepositorioTiposHabitacion.getInstancia();

        RepositorioHabitaciones repositorioHabitaciones = RepositorioHabitaciones.getInstancia();

        TipoHabitacion estandar = new TipoHabitacion(
                "Estándar",
                "Habitación para dos personas",
                2,
                150000
        );

        TipoHabitacion familiar = new TipoHabitacion(
                "Familiar",
                "Habitación para cuatro personas",
                4,
                250000
        );

        TipoHabitacion suite = new TipoHabitacion(
                "Suite",
                "Habitación amplia para dos personas",
                2,
                350000
        );

        repositorioTipos.guardar(estandar);
        repositorioTipos.guardar(familiar);
        repositorioTipos.guardar(suite);

        repositorioHabitaciones.guardar(
                new Habitacion("101", estandar, EstadoHabitacion.DISPONIBLE));

        repositorioHabitaciones.guardar(
                new Habitacion("102", estandar, EstadoHabitacion.DISPONIBLE));

        repositorioHabitaciones.guardar(
                new Habitacion("201", familiar, EstadoHabitacion.DISPONIBLE));

        repositorioHabitaciones.guardar(
                new Habitacion("202", familiar, EstadoHabitacion.DISPONIBLE));

        repositorioHabitaciones.guardar(
                new Habitacion("301", suite, EstadoHabitacion.DISPONIBLE));
    }

}
