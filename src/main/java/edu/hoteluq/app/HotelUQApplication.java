package edu.hoteluq.app;

import edu.hoteluq.datos.DatosIniciales;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HotelUQApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        DatosIniciales.cargarUsuariosIniciales();
        DatosIniciales.cargarHabitacionesIniciales();


        FXMLLoader fxmlLoader = new FXMLLoader(HotelUQApplication.class.getResource("/edu/hoteluq/Vistas/vistaInicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 650);
        stage.setTitle("HotelUQ - Sistema Integral de Gestión Hotelera");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}