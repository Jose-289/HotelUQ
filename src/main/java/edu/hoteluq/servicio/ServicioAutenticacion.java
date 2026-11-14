package edu.hoteluq.servicio;

import edu.hoteluq.modelo.Usuario;

public class ServicioAutenticacion {

    private final ServicioUsuarios servicioUsuarios;

    public ServicioAutenticacion(ServicioUsuarios servicioUsuarios){
        this.servicioUsuarios = servicioUsuarios;
    }

    public Usuario iniciarSesion(String correo, String contrasenia){

        Usuario usuario = servicioUsuarios.buscarPorCorreo(correo);

        if (usuario != null && usuario.getContrasenia().equals(contrasenia)){
            return usuario;
        }
        return null;
    }
}
