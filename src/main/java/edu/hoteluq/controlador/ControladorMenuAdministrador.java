package edu.hoteluq.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorMenuAdministrador {

    @FXML
    private Label textoBienvenida;

    public void mostrarBienvenida(String nombre){
        textoBienvenida.setText("Bienvenido, " + nombre);
    }

    public void abrirGestionClientes(ActionEvent evento) throws IOException{

        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/edu/hoteluq/Vistas/vistaGestionClientes.fxml"));

        Scene escena = new Scene(cargador.load());

        Stage ventana = (Stage) ((Node) evento.getSource()).getScene().getWindow();

        ventana.setScene(escena);
        ventana.setTitle("HotelUQ - Gestión de clientes");
        ventana.show();
    }
}
