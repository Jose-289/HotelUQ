package edu.hoteluq.servicio;

import edu.hoteluq.modelo.Usuario;
import edu.hoteluq.repositorio.RepositorioUsuarios;

import java.util.ArrayList;
import java.util.List;

public class ServicioUsuarios {

    private static final ServicioUsuarios instancia = new ServicioUsuarios();

    private final RepositorioUsuarios repositorioUsuarios;

    private ServicioUsuarios(){
        repositorioUsuarios = new RepositorioUsuarios();
    }

    public static ServicioUsuarios getInstancia(){
        return instancia;
    }

    public void agregarUsuario(Usuario usuario){
        repositorioUsuarios.guardar(usuario);
    }

    public Usuario buscarPorCorreo(String correo){
        return repositorioUsuarios.buscarPorCorreo(correo);
    }

    public Usuario buscarPorId(String id){
        return repositorioUsuarios.buscarPorId(id);
    }

    public List<Usuario> obtenerUsuarios(){
        return repositorioUsuarios.obtenerTodos();
    }
}
