package edu.hoteluq.patron.creacional.factory;

import edu.hoteluq.modelo.EstadoHabitacion;
import edu.hoteluq.modelo.Habitacion;
import edu.hoteluq.modelo.HabitacionFamiliar;

public class FabricaHabitacionFamiliar implements FabricaHabitacion {
    @Override
    public Habitacion crearHabitacion(String numero, EstadoHabitacion estado) {
        return new HabitacionFamiliar(numero, estado);
    }
    @Override
    public String getNombreTipo() {
        return "Familiar";
    }
}