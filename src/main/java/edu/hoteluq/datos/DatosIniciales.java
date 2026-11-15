package edu.hoteluq.datos;

import edu.hoteluq.modelo.*;
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

        Cliente cliente1 = new Cliente(
                "CLI-001",
                "Maria Jose Fajardo",
                "majofj@gmail.com",
                "3101234567",
                "cliente123",
                new TarjetaVirtual("TV-001", 500000, true),
                new CuentaPuntos(100)
        );

        Cliente cliente2 = new Cliente(
                "CLI-002",
                "Carlos Gómez",
                "carlos@gmail.com",
                "3207654321",
                "cliente123",
                new TarjetaVirtual("TV-002", 300000, true),
                new CuentaPuntos(50)
        );

        servicioUsuarios.agregarUsuario(cliente1);
        servicioUsuarios.agregarUsuario(cliente2);
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
