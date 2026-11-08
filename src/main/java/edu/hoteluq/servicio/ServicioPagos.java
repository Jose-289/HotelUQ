package edu.hoteluq.servicio;

import edu.hoteluq.modelo.MovimientoTarjeta;
import edu.hoteluq.modelo.Rol;
import edu.hoteluq.modelo.TarjetaVirtual;
import edu.hoteluq.modelo.TipoMovimiento;

import java.time.LocalDateTime;

public class ServicioPagos {

    public void recargarTarjeta(TarjetaVirtual tarjeta, double valor, Rol rol){
        if (rol == Rol.ADMINISTRADOR){
            tarjeta.recargar(valor);

            MovimientoTarjeta movimiento = new MovimientoTarjeta(
                    "Recarga" + (tarjeta.getMovimientos().size() + 1),
                    LocalDateTime.now(),
                    TipoMovimiento.RECARGA,
                    valor,
                    "Recarga de tarjeta"
            );
        }
    }
}
