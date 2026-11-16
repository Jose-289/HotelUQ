package edu.hoteluq.patron.comportamental.observer;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObservadorHistorialNotificaciones implements ObservadorHotel {

    private static final ObservadorHistorialNotificaciones instancia = new ObservadorHistorialNotificaciones();
    private final Map<String, List<String>> mensajesPorCliente = new HashMap<>();

    private ObservadorHistorialNotificaciones() {}

    public static ObservadorHistorialNotificaciones getInstancia() {
        return instancia;
    }

    @Override
    public void actualizar(EventoHotel evento) {
        String hora = evento.getFechaHora().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String mensaje = "[" + hora + "] " + evento.getDescripcion();
        mensajesPorCliente
                .computeIfAbsent(evento.getClienteId(), k -> new ArrayList<>())
                .add(0, mensaje);
    }

    public List<String> obtenerNotificacionesCliente(String clienteId) {
        return Collections.unmodifiableList(
                mensajesPorCliente.getOrDefault(clienteId, Collections.emptyList())
        );
    }
}