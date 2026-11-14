package edu.hoteluq.controlador;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorInicioSesion {

    public void volverInicio(ActionEvent evento) throws IOException{

        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/edu/hoteluq/Vistas/vistaInicio.fxml"));

        Scene escena = new Scene(cargador.load());

        Stage ventana = (Stage)((Node) evento.getSource()).getScene().getWindow();

        ventana.setScene(escena);
        ventana.setTitle("HotelUQ");
        ventana.show();
    }
}
