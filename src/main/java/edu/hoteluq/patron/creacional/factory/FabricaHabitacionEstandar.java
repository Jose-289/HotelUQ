package edu.hoteluq.patron.creacional.factory;

import edu.hoteluq.modelo.EstadoHabitacion;
import edu.hoteluq.modelo.Habitacion;
import edu.hoteluq.modelo.HabitacionEstandar;

public class FabricaHabitacionEstandar implements FabricaHabitacion {
    @Override
    public Habitacion crearHabitacion(String numero, EstadoHabitacion estado) {
        return new HabitacionEstandar(numero, estado);
    }
    @Override
    public String getNombreTipo() {
        return "Estándar";
    }
}