package edu.hoteluq.patron.estructural.decorator;

public abstract class ServicioAdicionalDecorator implements CostoReserva {
    protected final CostoReserva componente;

    public ServicioAdicionalDecorator(CostoReserva componente) {
        this.componente = componente;
    }

    @Override
    public double calcularCosto() {
        return componente.calcularCosto();
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion();
    }
}