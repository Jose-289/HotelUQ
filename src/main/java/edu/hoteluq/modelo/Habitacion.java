package edu.hoteluq.modelo;

public abstract class Habitacion {
    protected String numero;
    protected EstadoHabitacion estado;

    public Habitacion(String numero, EstadoHabitacion estado) {
        this.numero = numero;
        this.estado = estado != null ? estado : EstadoHabitacion.DISPONIBLE;
    }

    public String getNumero() {
        return numero;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public boolean estaDisponible() {
        return estado == EstadoHabitacion.DISPONIBLE;
    }

    public void cambiarEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    // Métodos abstractos que cada tipo concreto debe implementar (Polimorfismo puro)
    public abstract String getNombreTipo();
    public abstract String getDescripcion();
    public abstract int getCapacidad();
    public abstract double getTarifaNoche();

    public double calcularValor(int noches) {
        return getTarifaNoche() * noches;
    }
}
