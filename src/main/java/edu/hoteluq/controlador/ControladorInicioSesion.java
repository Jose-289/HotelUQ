package edu.hoteluq.controlador;


import edu.hoteluq.modelo.Usuario;
import edu.hoteluq.servicio.ServicioAutenticacion;
import edu.hoteluq.servicio.ServicioUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorInicioSesion {

    @FXML
    private TextField campoCorreo;

    @FXML
    private PasswordField campoContrasenia;

    private final ServicioAutenticacion servicioAutenticacion = new ServicioAutenticacion(ServicioUsuarios.getInstancia());

    public void iniciarSesion(ActionEvent evento) throws IOException{

        String correo = campoCorreo.getText().trim();
        String contrasenia = campoContrasenia.getText();

        if (correo.isEmpty() || contrasenia.isEmpty()){

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Inicio de sesión");
            alerta.setHeaderText(null);
            alerta.setContentText("Ingrese el correo y la contraseña.");
            alerta.showAndWait();

            return;
        }

        Usuario usuario = servicioAutenticacion.iniciarSesion(correo, contrasenia);

        if (usuario == null){

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Inicio de sesión");
            alerta.setHeaderText(null);
            alerta.setContentText("El correo o la contraseña son incorrectos.");
            alerta.showAndWait();

            return;
        }

        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/edu/hoteluq/Vistas/vistaMenuCliente.fxml"));

        Scene escena = new Scene(cargador.load());

        ControladorMenuCliente controlador = cargador.getController();

        controlador.mostrarBienvenida(usuario.getNombre());

        Stage ventana = (Stage) ((Node) evento.getSource()).getScene().getWindow();

        ventana.setScene(escena);
        ventana.setTitle("HotelUQ - Menú del cliente");
        ventana.show();
    }

    public void volverInicio(ActionEvent evento) throws IOException{

        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/edu/hoteluq/Vistas/vistaInicio.fxml"));

        Scene escena = new Scene(cargador.load());

        Stage ventana = (Stage)((Node) evento.getSource()).getScene().getWindow();

        ventana.setScene(escena);
        ventana.setTitle("HotelUQ");
        ventana.show();
    }
}
