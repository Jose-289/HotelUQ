package edu.hoteluq.servicio;

import edu.hoteluq.modelo.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class ServicioReembolsos {

    public double calcularReembolso(Reserva reserva, EstrategiaReembolso estrategia){

        long horasRestantes = Duration.between(LocalDateTime.now(), reserva.getFechaEntrada()).toHours();

        double valorPagado = reserva.getPago().getValor();

        GestorReembolso gestor = new GestorReembolso(estrategia);

        return gestor.calcularReembolso(valorPagado, horasRestantes);
    }

    public boolean puedeReembolsarse(Reserva reserva){
        return reserva.getPago() != null && reserva.getPago().getEstado() == EstadoPago.APROBADO;
    }

    public void procesarReembolso(Reserva reserva, EstrategiaReembolso estrategia){

        if (puedeReembolsarse(reserva)){
            double valorReembolso = calcularReembolso(reserva, estrategia);

            TarjetaVirtual tarjeta = reserva.getCliente().getTarjetaVirtual();

            if (valorReembolso > 0){

                tarjeta.reembolsar(valorReembolso);

                Reembolso reembolso = new Reembolso("Reembolso" + (reserva.getPago().getId()), LocalDateTime.now(),
                        valorReembolso, "Cancelación de reserva", true);

                reserva.getPago().asignarReembolso(reembolso);

                reserva.getPago().reembolsar();

                MovimientoTarjeta movimiento = new MovimientoTarjeta(reembolso.getId(), LocalDateTime.now(),
                        TipoMovimiento.REEMBOLSO, valorReembolso, "Reembolso de reserva");

                tarjeta.getMovimientos().add(movimiento);
            }

        }
    }
}
