package edu.hoteluq.model;

public class TipoHabitacion {

    private String nombre;
    private String descripcion;
    private int capacidad;
    private double tarifaNoche;

    public TipoHabitacion(String nombre, String descripcion, int capacidad, double tarifaNoche) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.tarifaNoche = tarifaNoche;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public double getTarifaNoche() {
        return tarifaNoche;
    }
}
