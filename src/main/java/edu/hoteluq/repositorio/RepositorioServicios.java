package edu.hoteluq.repositorio;

import edu.hoteluq.modelo.Servicio;
import java.util.ArrayList;
import java.util.List;

public class RepositorioServicios {
    private static final RepositorioServicios instancia = new RepositorioServicios();
    private final List<Servicio> servicios = new ArrayList<>();

    private RepositorioServicios() {
        // Datos iniciales de ejemplo para servicios
        servicios.add(new Servicio("SRV-01", "Desayuno Buffet", "Desayuno americano diario", 35000, true));
        servicios.add(new Servicio("SRV-02", "Parqueadero", "Vigilancia 24 horas", 20000, true));
        servicios.add(new Servicio("SRV-03", "Spa Relax", "Acceso a zona húmeda y masajes", 80000, true));
    }

    public static RepositorioServicios getInstancia() {
        return instancia;
    }

    public List<Servicio> obtenerTodos() {
        return List.copyOf(servicios);
    }

    public void guardar(Servicio servicio) {
        servicios.add(servicio);
    }

    public void eliminar(Servicio servicio) {
        servicios.remove(servicio);
    }

    public Servicio buscarPorId(String id) {
        for (Servicio s : servicios) {
            if (s.getId().equalsIgnoreCase(id)) return s;
        }
        return null;
    }
}