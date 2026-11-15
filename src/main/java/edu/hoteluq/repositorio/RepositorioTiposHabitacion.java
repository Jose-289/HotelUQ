package edu.hoteluq.repositorio;

import edu.hoteluq.modelo.TipoHabitacion;

import java.util.ArrayList;
import java.util.List;

public class RepositorioTiposHabitacion {

    private static final RepositorioTiposHabitacion instancia = new RepositorioTiposHabitacion();

    private final List<TipoHabitacion> tipos = new ArrayList<>();

    private RepositorioTiposHabitacion(){

    }

    public static RepositorioTiposHabitacion getInstancia(){
        return instancia;
    }

    public List<TipoHabitacion> obtenerTodos(){
        return List.copyOf(tipos);
    }

    public void guardar(TipoHabitacion tipo){
        tipos.add(tipo);
    }

    public TipoHabitacion buscarPorNombre(String nombre){

        for (TipoHabitacion tipo : tipos){

            if (tipo.getNombre().equalsIgnoreCase(nombre)){
                return tipo;
            }
        }
        return null;
    }

    public void eliminar(TipoHabitacion tipo){
        tipos.remove(tipo);
    }
}
