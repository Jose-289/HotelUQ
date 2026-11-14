package edu.hoteluq.servicio;

import edu.hoteluq.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ServicioUsuarios {

    private static final ServicioUsuarios instancia = new ServicioUsuarios();

    private final List<Usuario> usuarios;

    public ServicioUsuarios(){
        usuarios = new ArrayList<>();
    }

    public static ServicioUsuarios getInstancia(){
        return instancia;
    }

    public void agregarUsuario(Usuario usuario){
        usuarios.add(usuario);
    }

    public Usuario buscarPorCorreo(String correo){

        for (Usuario usuario : usuarios){

            if (usuario.getCorreo().equalsIgnoreCase(correo)){
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarporId(String id){

        for (Usuario usuario : usuarios){

            if (usuario.getId().equalsIgnoreCase(id)){
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> obtenerUsuarios(){
        return usuarios;
    }
}
