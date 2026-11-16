package edu.hoteluq.patron.creacional.factory;

import edu.hoteluq.modelo.EstadoHabitacion;
import edu.hoteluq.modelo.Habitacion;
import edu.hoteluq.modelo.HabitacionSuite;

public class FabricaHabitacionSuite implements FabricaHabitacion {
    @Override
    public Habitacion crearHabitacion(String numero, EstadoHabitacion estado) {
        return new HabitacionSuite(numero, estado);
    }
    @Override
    public String getNombreTipo() {
        return "Suite";
    }
}