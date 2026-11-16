package edu.hoteluq.patron.estructural.facade;

import edu.hoteluq.modelo.*;
import edu.hoteluq.patron.comportamental.observer.EventoHotel;
import edu.hoteluq.patron.comportamental.observer.GestorEventosHotel;
import edu.hoteluq.patron.comportamental.observer.ObservadorHistorialNotificaciones;
import edu.hoteluq.patron.comportamental.state.ContextoFidelizacion;
import edu.hoteluq.patron.estructural.proxy.OperacionBilletera;
import edu.hoteluq.patron.estructural.proxy.ProxyBilleteraVirtual;
import edu.hoteluq.servicio.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FachadaClienteHotel {

    private static final FachadaClienteHotel instancia = new FachadaClienteHotel();

    private final ServicioHabitaciones servicioHabitaciones = ServicioHabitaciones.getInstancia();
    private final ServicioServiciosAdicionales servicioAdicionales = ServicioServiciosAdicionales.getInstancia();
    private final ServicioPromociones servicioPromociones = ServicioPromociones.getInstancia();
    private final ServicioReservas servicioReservas = ServicioReservas.getInstancia();
    private final ServicioFidelizacion servicioFidelizacion = ServicioFidelizacion.getInstancia();

    private final Map<String, OperacionBilletera> billeterasProxy = new HashMap<>();

    private FachadaClienteHotel() {}

    public static FachadaClienteHotel getInstancia() {
        return instancia;
    }

    public OperacionBilletera obtenerBilletera(Cliente cliente) {
        return billeterasProxy.computeIfAbsent(cliente.getId(), id -> new ProxyBilleteraVirtual(cliente));
    }

    public ContextoFidelizacion obtenerEstadoFidelizacion(Cliente cliente) {
        return servicioFidelizacion.obtenerContexto(cliente);
    }

    public List<Habitacion> obtenerHabitacionesDisponibles() {
        return servicioHabitaciones.obtenerTodasHabitaciones().stream()
                .filter(Habitacion::estaDisponible)
                .collect(Collectors.toList());
    }

    public List<Servicio> obtenerServiciosDisponibles() {
        return servicioAdicionales.obtenerServiciosDisponibles().stream()
                .filter(Servicio::isDisponible)
                .collect(Collectors.toList());
    }

    public List<Promocion> obtenerPromocionesVigentes() {
        return servicioPromociones.obtenerPromociones().stream()
                .filter(Promocion::estaVigente)
                .collect(Collectors.toList());
    }

    public List<Reserva> obtenerMisReservas(Cliente cliente) {
        return servicioReservas.obtenerReservasPorCliente(cliente);
    }

    public List<String> obtenerNotificaciones(Cliente cliente) {
        return ObservadorHistorialNotificaciones.getInstancia().obtenerNotificacionesCliente(cliente.getId());
    }

    public ServicioReservas.ResultadoCotizacion cotizarEstadia(Habitacion habitacion, LocalDateTime entrada,
                                                              LocalDateTime salida, List<Servicio> servicios,
                                                              Promocion promocion) {
        return servicioReservas.cotizarReserva(habitacion, entrada, salida, servicios, promocion);
    }

    /**
     * Orquesta reserva + cobro + notificación Observer + acumulación de puntos State en un solo paso.
     */
    public Reserva reservarEstadiaCompleta(Cliente cliente, Habitacion habitacion,
                                           LocalDateTime entrada, LocalDateTime salida,
                                           List<Servicio> servicios, Promocion promocion) {
        ServicioReservas.ResultadoCotizacion cotizacion = servicioReservas.cotizarReserva(
                habitacion, entrada, salida, servicios, promocion
        );
        if (promocion != null && !cotizacion.promoAplicada()) {
            throw new IllegalArgumentException("La promoción seleccionada no aplica para esta reserva.");
        }

        Reserva reserva = servicioReservas.crearYConfirmarReserva(
                cliente, habitacion, entrada, salida, servicios, promocion
        );

        GestorEventosHotel.getInstancia().notificar(
                new EventoHotel(
                        EventoHotel.TipoEvento.RESERVA_CONFIRMADA,
                        cliente.getId(),
                        String.format("Reserva %s confirmada (Hab. %s) por $%.0f",
                                reserva.getId(), habitacion.getNumero(), reserva.getValorTotal()),
                        reserva.getValorTotal()
                )
        );

        return reserva;
    }

    public void recargarBilletera(Cliente cliente, double monto) {
        obtenerBilletera(cliente).recargarSaldo(monto);
    }

    public double canjearPuntos(Cliente cliente, int puntos) {
        return servicioFidelizacion.canjearPuntos(cliente, puntos);
    }
}