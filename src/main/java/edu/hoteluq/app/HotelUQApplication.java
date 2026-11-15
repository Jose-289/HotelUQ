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

        FXMLLoader fxmlLoader = new FXMLLoader(HotelUQApplication.class.getResource("/edu/hoteluq/vistas/vistaInicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("HotelUQ");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
