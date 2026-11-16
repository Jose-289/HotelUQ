package edu.hoteluq.patron.comportamental.state;

import edu.hoteluq.modelo.Cliente;

public class ContextoFidelizacion {

    private final Cliente cliente;
    private EstadoFidelizacion estado;

    public ContextoFidelizacion(Cliente cliente) {
        this.cliente = cliente;
        this.estado = new NivelBronce();
        this.estado.evaluarTransicion(this);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getPuntosAcumulados() {
        return cliente.getCuentaPuntos().getPuntosAcumulados();
    }

    public EstadoFidelizacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoFidelizacion nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public int acreditarPorCompra(double montoPagado) {
        int puntosBase = (int) (montoPagado / 1000.0);
        int puntosGanados = (int) Math.round(puntosBase * estado.getMultiplicadorPuntos());
        if (puntosGanados > 0) {
            cliente.getCuentaPuntos().acumularPuntos(puntosGanados);
            estado.evaluarTransicion(this);
        }
        return puntosGanados;
    }

    public double canjearPuntosPorSaldo(int puntosACanjear) {
        if (puntosACanjear <= 0 || puntosACanjear > getPuntosAcumulados()) {
            throw new IllegalArgumentException("Cantidad de puntos inválida o insuficiente.");
        }
        // Equivalencia base: 1 punto = $50 COP + bono por nivel State
        double valorBase = puntosACanjear * 50.0;
        double valorFinal = valorBase * (1.0 + estado.getPorcentajeBonoCanje());

        cliente.getCuentaPuntos().redimirPuntos(puntosACanjear);
        cliente.getTarjetaVirtual().recargar(valorFinal);
        estado.evaluarTransicion(this);

        return valorFinal;
    }
}