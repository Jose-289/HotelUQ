package edu.hoteluq.patron.estructural.decorator;

import edu.hoteluq.modelo.Servicio;

public class ServicioDecorator extends ServicioAdicionalDecorator {
    private final Servicio servicio;

    public ServicioDecorator(CostoReserva componente, Servicio servicio) {
        super(componente);
        this.servicio = servicio;
    }

    @Override
    public double calcularCosto() {
        return super.calcularCosto() + servicio.getPrecio();
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + " + servicio.getNombre() + " ($" + servicio.getPrecio() + ")";
    }
}