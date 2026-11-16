package edu.hoteluq.modelo;

public class HabitacionFamiliar extends Habitacion {

    public HabitacionFamiliar(String numero, EstadoHabitacion estado) {
        super(numero, estado);
    }

    @Override
    public String getNombreTipo() { return "Familiar"; }

    @Override
    public String getDescripcion() { return "Habitación amplia para cuatro personas"; }

    @Override
    public int getCapacidad() { return 4; }

    @Override
    public double getTarifaNoche() { return 250000.0; }
}