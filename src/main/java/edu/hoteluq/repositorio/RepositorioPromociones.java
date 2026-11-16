package edu.hoteluq.repositorio;

import edu.hoteluq.modelo.Promocion;
import edu.hoteluq.patron.comportamental.strategy.DescuentoMontoFijoStrategy;
import edu.hoteluq.patron.comportamental.strategy.DescuentoPorcentajeStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPromociones {
    private static final RepositorioPromociones instancia = new RepositorioPromociones();
    private final List<Promocion> promociones = new ArrayList<>();

    private RepositorioPromociones() {
        promociones.add(new Promocion(
                "DESC15",
                "15% de descuento por estadía larga",
                LocalDate.now().minusDays(5),
                LocalDate.now().plusMonths(2),
                new DescuentoPorcentajeStrategy(15.0, 3),
                true
        ));
        promociones.add(new Promocion(
                "BONO50K",
                "$50.000 de descuento en reservas superiores a $300.000",
                LocalDate.now().minusDays(5),
                LocalDate.now().plusMonths(1),
                new DescuentoMontoFijoStrategy(50000.0, 300000.0),
                true
        ));
    }

    public static RepositorioPromociones getInstancia() {
        return instancia;
    }

    public List<Promocion> obtenerTodas() {
        return List.copyOf(promociones);
    }

    public void guardar(Promocion promocion) {
        promociones.add(promocion);
    }

    public void eliminar(Promocion promocion) {
        promociones.remove(promocion);
    }

    public Promocion buscarPorCodigo(String codigo) {
        for (Promocion p : promociones) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) return p;
        }
        return null;
    }
}