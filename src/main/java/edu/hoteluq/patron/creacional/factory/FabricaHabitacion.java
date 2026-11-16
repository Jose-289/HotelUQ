package edu.hoteluq.patron.creacional.factory;

import edu.hoteluq.modelo.EstadoHabitacion;
import edu.hoteluq.modelo.Habitacion;

public interface FabricaHabitacion {
    Habitacion crearHabitacion(String numero, EstadoHabitacion estado);
    String getNombreTipo();
}