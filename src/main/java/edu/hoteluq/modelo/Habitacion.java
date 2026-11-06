package edu.hoteluq.modelo;

import java.time.LocalDate;

public class Habitacion {

    private String numero;
    private TipoHabitacion tipo;
    private EstadoHabitacion estado;

    public Habitacion(String numero, TipoHabitacion tipo, EstadoHabitacion estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public boolean estaDisponible(){
        return estado == EstadoHabitacion.DISPONIBLE;
    }

    public void cambiarEstado(EstadoHabitacion estado){
        this.estado = estado;
    }

}
