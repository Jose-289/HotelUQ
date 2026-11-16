package edu.hoteluq.patron.creacional.builder;

import edu.hoteluq.modelo.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservaBuilder {

    private String id;
    private Cliente cliente;
    private Habitacion habitacion;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private EstadoReserva estado;
    private final List<Servicio> servicios;
    private Promocion promocion;

    public ReservaBuilder() {
        this.id = "RES-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.estado = EstadoReserva.PENDIENTE;
        this.servicios = new ArrayList<>();
    }

    public ReservaBuilder conId(String id) {
        if (id != null && !id.trim().isEmpty()) {
            this.id = id;
        }
        return this;
    }

    public ReservaBuilder paraCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public ReservaBuilder enHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
        return this;
    }

    public ReservaBuilder conFechas(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        return this;
    }

    public ReservaBuilder conEstado(EstadoReserva estado) {
        if (estado != null) {
            this.estado = estado;
        }
        return this;
    }

    public ReservaBuilder agregarServicio(Servicio servicio) {
        if (servicio != null && servicio.isDisponible()) {
            this.servicios.add(servicio);
        }
        return this;
    }

    public ReservaBuilder agregarServicios(List<Servicio> listaServicios) {
        if (listaServicios != null) {
            for (Servicio s : listaServicios) {
                agregarServicio(s);
            }
        }
        return this;
    }

    public ReservaBuilder conPromocion(Promocion promocion) {
        this.promocion = promocion;
        return this;
    }

    public Reserva build() {
        if (cliente == null) {
            throw new IllegalStateException("La reserva debe tener un cliente asociado.");
        }
        if (habitacion == null) {
            throw new IllegalStateException("La reserva debe tener una habitación asociada.");
        }
        if (fechaEntrada == null || fechaSalida == null) {
            throw new IllegalStateException("Las fechas de entrada y salida son obligatorias.");
        }
        if (!fechaSalida.isAfter(fechaEntrada)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.");
        }

        // Instanciamos la reserva base y le agregamos sus componentes
        Reserva reserva = new Reserva(id, cliente, habitacion, fechaEntrada, fechaSalida, 0.0, estado);

        for (Servicio s : servicios) {
            reserva.agregarServicio(s);
        }

        if (promocion != null) {
            reserva.aplicarPromocion(promocion);
        }

        // Recalcular y fijar el valor total definitivo
        reserva.recalcularValorTotal();

        return reserva;
    }
}