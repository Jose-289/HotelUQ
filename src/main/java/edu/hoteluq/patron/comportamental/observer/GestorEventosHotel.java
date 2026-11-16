package edu.hoteluq.patron.comportamental.observer;

import java.util.ArrayList;
import java.util.List;

public class GestorEventosHotel {

    private static final GestorEventosHotel instancia = new GestorEventosHotel();
    private final List<ObservadorHotel> observadores = new ArrayList<>();

    private GestorEventosHotel() {}

    public static GestorEventosHotel getInstancia() {
        return instancia;
    }

    public void suscribir(ObservadorHotel observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void desuscribir(ObservadorHotel observador) {
        observadores.remove(observador);
    }

    public void notificar(EventoHotel evento) {
        for (ObservadorHotel obs : new ArrayList<>(observadores)) {
            obs.actualizar(evento);
        }
    }
}