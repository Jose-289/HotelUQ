package edu.hoteluq.model;

public class Cliente {

    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasenia;

    public Cliente(String id, String nombre, String correo, String telefono, String contrasenia) {

        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
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
}
