package edu.hoteluq.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorInicio {

    public void abrirInicioSesion(ActionEvent evento) throws IOException{

        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/edu/hoteluq/Vistas/vistaInicioSesion.fxml"));

        Scene escena = new Scene(cargador.load());

        Stage ventana = (Stage) ((javafx.scene.Node) evento.getSource()).getScene().getWindow();

        ventana.setScene(escena);
        ventana.setTitle("HotelUQ - Inicio de sesión");
        ventana.show();
    }
}
