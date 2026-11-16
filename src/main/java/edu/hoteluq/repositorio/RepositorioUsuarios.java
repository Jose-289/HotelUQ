package edu.hoteluq.repositorio;

import edu.hoteluq.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {

    private final List<Usuario> usuarios = new ArrayList<>();

    public List<Usuario> obtenerTodos(){
        return List.copyOf(usuarios);
    }

    public void guardar(Usuario usuario){
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

    public Usuario buscarPorId(String id){

        for (Usuario usuario : usuarios){

            if (usuario.getId().equalsIgnoreCase(id)){
                return usuario;
            }
        }
        return null;
   }

   public void eliminar(Usuario usuario){
        usuarios.remove(usuario);
   }
}
