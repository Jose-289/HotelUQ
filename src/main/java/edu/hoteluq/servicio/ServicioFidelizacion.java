package edu.hoteluq.servicio;

import edu.hoteluq.modelo.Cliente;
import edu.hoteluq.modelo.Usuario;
import edu.hoteluq.patron.comportamental.observer.EventoHotel;
import edu.hoteluq.patron.comportamental.observer.GestorEventosHotel;
import edu.hoteluq.patron.comportamental.observer.ObservadorHistorialNotificaciones;
import edu.hoteluq.patron.comportamental.observer.ObservadorHotel;
import edu.hoteluq.patron.comportamental.state.ContextoFidelizacion;

import java.util.HashMap;
import java.util.Map;

public class ServicioFidelizacion implements ObservadorHotel {

    private static final ServicioFidelizacion instancia = new ServicioFidelizacion();
    private final Map<String, ContextoFidelizacion> contextos = new HashMap<>();

    private ServicioFidelizacion() {
        GestorEventosHotel gestor = GestorEventosHotel.getInstancia();
        gestor.suscribir(this);
        gestor.suscribir(ObservadorHistorialNotificaciones.getInstancia());
    }

    public static ServicioFidelizacion getInstancia() {
        return instancia;
    }

    public ContextoFidelizacion obtenerContexto(Cliente cliente) {
        return contextos.computeIfAbsent(cliente.getId(), id -> new ContextoFidelizacion(cliente));
    }

    @Override
    public void actualizar(EventoHotel evento) {
        if (evento.getTipo() == EventoHotel.TipoEvento.RESERVA_CONFIRMADA) {
            Cliente cliente = buscarClientePorId(evento.getClienteId());
            if (cliente != null) {
                ContextoFidelizacion ctx = obtenerContexto(cliente);
                int ganados = ctx.acreditarPorCompra(evento.getMonto());
                if (ganados > 0) {
                    ObservadorHistorialNotificaciones.getInstancia().actualizar(
                            new EventoHotel(
                                    EventoHotel.TipoEvento.CANJE_PUNTOS,
                                    cliente.getId(),
                                    "¡Ganaste +" + ganados + " pts! Nivel actual: " + ctx.getEstado().getNombreNivel(),
                                    ganados
                            )
                    );
                }
            }
        }
    }

    public double canjearPuntos(Cliente cliente, int puntos) {
        ContextoFidelizacion ctx = obtenerContexto(cliente);
        double saldoObtenido = ctx.canjearPuntosPorSaldo(puntos);
        GestorEventosHotel.getInstancia().notificar(
                new EventoHotel(
                        EventoHotel.TipoEvento.CANJE_PUNTOS,
                        cliente.getId(),
                        String.format("Canjeaste %d pts por $%.0f en tu tarjeta virtual (Nivel %s)",
                                puntos, saldoObtenido, ctx.getEstado().getNombreNivel()),
                        saldoObtenido
                )
        );
        return saldoObtenido;
    }

    private Cliente buscarClientePorId(String id) {
        for (Usuario u : ServicioUsuarios.getInstancia().obtenerUsuarios()) {
            if (u instanceof Cliente && u.getId().equals(id)) {
                return (Cliente) u;
            }
        }
        return null;
    }
}