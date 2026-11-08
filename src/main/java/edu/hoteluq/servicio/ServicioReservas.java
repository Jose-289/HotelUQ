package edu.hoteluq.servicio;

import edu.hoteluq.modelo.EstadoPago;
import edu.hoteluq.modelo.EstadoReserva;
import edu.hoteluq.modelo.Habitacion;
import edu.hoteluq.modelo.Reserva;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServicioReservas {

    private List<Reserva> reservas;

    public ServicioReservas(){
        reservas = new ArrayList<>();
    }

    public void agregarReserva(Reserva reserva){
        reservas.add(reserva);
    }

    public boolean confirmarReserva(Reserva reserva){
        if (estaDisponible(reserva.getHabitacion(), reserva.getFechaEntrada(), reserva.getFechaSalida())
                && reserva.getPago() != null && reserva.getPago().getEstado() == EstadoPago.APROBADO) {

            reserva.confirmar();
            return true;

        }

        return false;
    }

    public boolean estaDisponible(Habitacion habitacion, LocalDateTime fechaEntrada,
                                  LocalDateTime fechaSalida){

        for (Reserva reserva : reservas){

            if (reserva.getHabitacion().equals(habitacion)){

                if (reserva.getEstado() != EstadoReserva.CANCELADA){

                    if (fechaEntrada.isBefore(reserva.getFechaSalida())
                            && fechaSalida.isAfter(reserva.getFechaEntrada())){

                        return false;
                    }
                }

            }
        }

        return true;
    }

}
