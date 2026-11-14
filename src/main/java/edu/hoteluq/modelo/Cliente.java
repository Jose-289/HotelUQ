package edu.hoteluq.modelo;

public class Cliente extends Usuario{

    private TarjetaVirtual tarjetaVirtual;
    private CuentaPuntos cuentaPuntos;

    public Cliente(String id, String nombre, String correo, String telefono, String contrasenia,
                   TarjetaVirtual tarjetaVirtual, CuentaPuntos cuentaPuntos) {

        super(id, nombre, correo, telefono, contrasenia, Rol.CLIENTE);

        this.tarjetaVirtual = tarjetaVirtual;
        this.cuentaPuntos = cuentaPuntos;

    }

    public TarjetaVirtual getTarjetaVirtual() {
        return tarjetaVirtual;
    }

    public CuentaPuntos getCuentaPuntos() {
        return cuentaPuntos;
    }

    public void registrarse(){


    }

    public void iniciarSesion(){


    }

    public void consultarReserva(){


    }

    public void solicitarCancelacion(){


    }

    public void consultarSaldo(){


    }

    public void consultarPuntos(){


    }
}
