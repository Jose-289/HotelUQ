package edu.hoteluq.modelo;

public class HabitacionEstandar extends Habitacion {

    public HabitacionEstandar(String numero, EstadoHabitacion estado) {
        super(numero, estado);
    }

    @Override
    public String getNombreTipo() { return "Estándar"; }

    @Override
    public String getDescripcion() { return "Habitación cómoda para dos personas"; }

    @Override
    public int getCapacidad() { return 2; }

    @Override
    public double getTarifaNoche() { return 150000.0; }
}