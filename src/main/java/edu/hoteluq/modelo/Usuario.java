package edu.hoteluq.modelo;

public abstract class Usuario {

    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasenia;
    private Rol rol;

    public Usuario(String id, String nombre, String correo, String telefono, String contrasenia, Rol rol){

        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
        this.rol = rol;
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

    public Rol getRol() {
        return rol;
    }
}
