package edu.hoteluq.modelo;

public class Cliente {

    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasenia;
    private TarjetaVirtual tarjetaVirtual;
    private CuentaPuntos cuentaPuntos;

    public Cliente(String id, String nombre, String correo, String telefono, String contrasenia,
                   TarjetaVirtual tarjetaVirtual, CuentaPuntos cuentaPuntos) {

        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
        this.tarjetaVirtual = tarjetaVirtual;
        this.cuentaPuntos = cuentaPuntos;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getContrasenia() {
        return contrasenia;
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
