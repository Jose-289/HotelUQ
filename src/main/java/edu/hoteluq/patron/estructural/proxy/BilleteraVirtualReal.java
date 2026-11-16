package edu.hoteluq.patron.estructural.proxy;

import edu.hoteluq.modelo.TarjetaVirtual;
import java.util.Collections;
import java.util.List;

public class BilleteraVirtualReal implements OperacionBilletera {

    private final TarjetaVirtual tarjeta;

    public BilleteraVirtualReal(TarjetaVirtual tarjeta) {
        this.tarjeta = tarjeta;
    }

    @Override
    public double consultarSaldo() {
        return tarjeta.getSaldo();
    }

    @Override
    public boolean estaActiva() {
        return tarjeta.isActiva();
    }

    @Override
    public void recargarSaldo(double monto) {
        tarjeta.recargar(monto);
    }

    @Override
    public List<String> obtenerHistorialAuditoria() {
        return Collections.emptyList();
    }
}