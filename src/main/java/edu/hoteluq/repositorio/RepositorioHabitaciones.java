package edu.hoteluq.repositorio;

import edu.hoteluq.modelo.Habitacion;

import java.util.ArrayList;
import java.util.List;

public class RepositorioHabitaciones {

    private static final RepositorioHabitaciones instancia = new RepositorioHabitaciones();

    private final List<Habitacion> habitaciones = new ArrayList<>();

    private RepositorioHabitaciones(){

    }

    public static RepositorioHabitaciones getInstancia(){
        return instancia;
    }

    public List<Habitacion> obtenerTodas(){
        return List.copyOf(habitaciones);
    }

    public void guardar(Habitacion habitacion){
        habitaciones.add(habitacion);
    }

    public Habitacion buscarPorNumero(String numero){

        for (Habitacion habitacion : habitaciones){

            if (habitacion.getNumero().equalsIgnoreCase(numero)){
                return habitacion;
            }
        }
        return null;
    }

    public void eliminar(Habitacion habitacion){
        habitaciones.remove(habitacion);
    }
}
