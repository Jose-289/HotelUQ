package edu.hoteluq.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControladorMenuCliente {

    @FXML
    private Label textoBienvenida;

    public void mostrarBienvenida(String nombre){
        textoBienvenida.setText("Bienvenido, " + nombre);
    }
}
