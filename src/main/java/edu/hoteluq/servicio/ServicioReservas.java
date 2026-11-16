package edu.hoteluq.servicio;

import edu.hoteluq.modelo.*;
import edu.hoteluq.patron.creacional.builder.ReservaBuilder;
import edu.hoteluq.patron.estructural.decorator.CostoReserva;
import edu.hoteluq.patron.estructural.decorator.ReservaBaseDecorator;
import edu.hoteluq.patron.estructural.decorator.ServicioDecorator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServicioReservas {

    private static final ServicioReservas instancia = new ServicioReservas();
    private final List<Reserva> reservas;

    private ServicioReservas() {
        this.reservas = new ArrayList<>();
    }

    public static ServicioReservas getInstancia() {
        return instancia;
    }

    public List<Reserva> obtenerTodasReservas() {
        return List.copyOf(reservas);
    }

    public List<Reserva> obtenerReservasPorCliente(Cliente cliente) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getCliente().getId().equals(cliente.getId())) {
                resultado.add(r);
            }
        }
        return resultado;
    }

     //Calcula el precio estimado y valida promociones usando Decorator y Strategy

    public ResultadoCotizacion cotizarReserva(Habitacion habitacion, LocalDateTime entrada, LocalDateTime salida,
                                              List<Servicio> servicios, Promocion promocion) {
        Cotizable cotizacion = new CotizacionReserva(habitacion, entrada, salida, servicios);

        // 1. Aplicar Patrón Decorator (Habitación base + Servicios adicionales)
        CostoReserva costoDecorado = new ReservaBaseDecorator(cotizacion);
        if (servicios != null) {
            for (Servicio s : servicios) {
                costoDecorado = new ServicioDecorator(costoDecorado, s);
            }
        }

        double subtotal = costoDecorado.calcularCosto();
        double descuento = 0.0;
        boolean promoAplicada = false;

        // 2. Aplicar Patrón Strategy (Descuento si la promoción es vigente y aplicable)
        if (promocion != null && promocion.estaVigente() && promocion.getEstrategia().esAplicable(cotizacion)) {
            descuento = promocion.calcularDescuento(subtotal);
            promoAplicada = true;
        }

        double totalFinal = Math.max(0.0, subtotal - descuento);
        return new ResultadoCotizacion(subtotal, descuento, totalFinal, promoAplicada, costoDecorado.getDescripcion());
    }

    /**
     * Crea, cobra y confirma una reserva real usando el Patrón Builder.
     */
    public Reserva crearYConfirmarReserva(Cliente cliente, Habitacion habitacion,
                                          LocalDateTime entrada, LocalDateTime salida,
                                          List<Servicio> servicios, Promocion promocion) {

        if (!habitacion.estaDisponible() && habitacion.getEstado() == EstadoHabitacion.MANTENIMIENTO) {
            throw new IllegalStateException("La habitación se encuentra en mantenimiento y no puede reservarse.");
        }

        if (!estaDisponible(habitacion, entrada, salida)) {
            throw new IllegalStateException("La habitación " + habitacion.getNumero() + " ya está reservada en ese rango de fechas.");
        }

        // Construcción mediante el Patrón Builder
        Reserva reserva = new ReservaBuilder()
                .paraCliente(cliente)
                .enHabitacion(habitacion)
                .conFechas(entrada, salida)
                .agregarServicios(servicios)
                .conPromocion(promocion)
                .build();

        // Procesar pago con la tarjeta virtual del cliente
        double totalAPagar = reserva.getValorTotal();
        Pago pago = new Pago(
                "PAG-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase(),
                LocalDateTime.now(),
                totalAPagar,
                EstadoPago.PENDIENTE,
                reserva
        );

        pago.procesar();

        if (pago.getEstado() != EstadoPago.APROBADO) {
            throw new IllegalStateException("Pago rechazado: la tarjeta virtual está inactiva o no tiene saldo suficiente ($" + totalAPagar + ").");
        }

        reserva.confirmar();
        habitacion.cambiarEstado(EstadoHabitacion.OCUPADA);
        reservas.add(reserva);

        return reserva;
    }

    public boolean estaDisponible(Habitacion habitacion, LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        for (Reserva reserva : reservas) {
            if (reserva.getHabitacion().getNumero().equalsIgnoreCase(habitacion.getNumero())) {
                if (reserva.getEstado() != EstadoReserva.CANCELADA) {
                    if (fechaEntrada.isBefore(reserva.getFechaSalida())
                            && fechaSalida.isAfter(reserva.getFechaEntrada())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void cancelarReservaPorHotel(Reserva reserva) {
        if (reserva == null || reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw new IllegalStateException("La reserva no es válida o ya fue cancelada.");
        }
        ServicioReembolsos servicioReembolsos = new ServicioReembolsos();
        servicioReembolsos.procesarReembolso(reserva, new ReembolsoHotel());
        reserva.cancelar();
        reserva.getHabitacion().cambiarEstado(EstadoHabitacion.DISPONIBLE);
    }

    /**
     * Contenedor inmutable para devolver todos los datos de la cotización al controlador.
     */
    public record ResultadoCotizacion(double subtotal, double descuento, double totalFinal,
                                      boolean promoAplicada, String descripcion) {}
}
