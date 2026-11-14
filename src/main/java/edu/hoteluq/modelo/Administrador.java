package edu.hoteluq.modelo;

public class Administrador extends Usuario{

    public Administrador(String id, String nombre, String correo, String telefono, String contrasenia){

        super(id, nombre, correo, telefono, contrasenia, Rol.ADMINISTRADOR);
    }

    public void gestionarClientes(){

    }

    public void gestionarHabitaciones(){

    }

    public void gestionarReservas(){

    }

    public void gestionarServicios(){

    }

    public void gestionarPromociones(){

    }

    public void generarReportes(){

    }
}
