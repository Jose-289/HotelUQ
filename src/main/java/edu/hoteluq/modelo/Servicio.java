package edu.hoteluq.modelo;

public class Servicio {

    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;

    public Servicio(String id, String nombre, String descripcion, double precio, boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void cambiarDisponibilidad(boolean disponible){
        this.disponible = disponible;
    }
}
