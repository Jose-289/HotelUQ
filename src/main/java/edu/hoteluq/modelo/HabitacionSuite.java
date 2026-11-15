package edu.hoteluq.modelo;

public class HabitacionSuite extends Habitacion {

    public HabitacionSuite(String numero, EstadoHabitacion estado) {
        super(numero, estado);
    }

    @Override
    public String getNombreTipo() { return "Suite"; }

    @Override
    public String getDescripcion() { return "Suite de lujo para dos personas"; }

    @Override
    public int getCapacidad() { return 2; }

    @Override
    public double getTarifaNoche() { return 350000.0; }
}