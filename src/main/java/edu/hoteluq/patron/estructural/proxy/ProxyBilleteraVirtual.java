package edu.hoteluq.patron.estructural.proxy;

import edu.hoteluq.modelo.Cliente;
import edu.hoteluq.patron.comportamental.observer.EventoHotel;
import edu.hoteluq.patron.comportamental.observer.GestorEventosHotel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProxyBilleteraVirtual implements OperacionBilletera {

    private static final double MONTO_MINIMO_RECARGA = 10000.0;
    private static final double MONTO_MAXIMO_RECARGA = 10000000.0;

    private final Cliente cliente;
    private final BilleteraVirtualReal billeteraReal;
    private final List<String> historialAuditoria = new ArrayList<>();

    public ProxyBilleteraVirtual(Cliente cliente) {
        this.cliente = cliente;
        this.billeteraReal = new BilleteraVirtualReal(cliente.getTarjetaVirtual());
    }

    @Override
    public double consultarSaldo() {
        if (cliente.getTarjetaVirtual() == null) {
            throw new IllegalStateException("El cliente no tiene una tarjeta virtual asociada.");
        }
        return billeteraReal.consultarSaldo();
    }

    @Override
    public boolean estaActiva() {
        return cliente.getTarjetaVirtual() != null && billeteraReal.estaActiva();
    }

    @Override
    public void recargarSaldo(double monto) {
        if (!estaActiva()) {
            registrarAuditoria("RECHAZO RECARGA: Tarjeta virtual inactiva.");
            throw new IllegalStateException("Operación denegada: su tarjeta virtual se encuentra inactiva.");
        }
        if (monto < MONTO_MINIMO_RECARGA || monto > MONTO_MAXIMO_RECARGA) {
            registrarAuditoria("RECHAZO RECARGA: Monto fuera de rango ($" + monto + ").");
            throw new IllegalArgumentException(
                    String.format("El monto a recargar debe estar entre $%.0f y $%.0f.",
                            MONTO_MINIMO_RECARGA, MONTO_MAXIMO_RECARGA)
            );
        }

        billeteraReal.recargarSaldo(monto);
        registrarAuditoria(String.format("RECARGA EXITOSA: +$%.2f | Nuevo saldo: $%.2f", monto, consultarSaldo()));

        GestorEventosHotel.getInstancia().notificar(
                new EventoHotel(
                        EventoHotel.TipoEvento.RECARGA_BILLETERA,
                        cliente.getId(),
                        String.format("Recarga aprobada en tarjeta virtual por $%.0f", monto),
                        monto
                )
        );
    }

    private void registrarAuditoria(String detalle) {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        historialAuditoria.add(0, "[" + ts + "] " + detalle);
    }

    @Override
    public List<String> obtenerHistorialAuditoria() {
        return Collections.unmodifiableList(historialAuditoria);
    }
}