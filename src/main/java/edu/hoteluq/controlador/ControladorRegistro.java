package edu.hoteluq.controlador;

import edu.hoteluq.modelo.Cliente;
import edu.hoteluq.modelo.CuentaPuntos;
import edu.hoteluq.modelo.TarjetaVirtual;
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

public class ControladorRegistro {

    @FXML
    private TextField campoDocumento;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoCorreo;

    @FXML
    private TextField campoTelefono;

    @FXML
    private PasswordField campoContrasenia;

    private final ServicioUsuarios servicioUsuarios = ServicioUsuarios.getInstancia();

    public void volverInicio(ActionEvent evento) throws IOException{

        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/edu/hoteluq/Vistas/vistaInicio.fxml"));

        Scene escena = new Scene(cargador.load());

        Stage ventana = (Stage) ((Node) evento.getSource()).getScene().getWindow();

        ventana.setScene(escena);
        ventana.setTitle("HotelUQ");
        ventana.show();
    }

    public void registrarCliente(){

        String documento = campoDocumento.getText().trim();
        String nombre = campoNombre.getText().trim();
        String correo = campoCorreo.getText().trim();
        String telefono = campoTelefono.getText().trim();
        String contrasenia = campoContrasenia.getText().trim();

        if (documento.isEmpty() || nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contrasenia.isEmpty()){

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Registro");
            alerta.setHeaderText(null);
            alerta.setContentText("Todos los campos son obligatorios");
            alerta.showAndWait();

            return;
        }

        if (servicioUsuarios.buscarporId(documento) != null){

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Registro");
            alerta.setHeaderText(null);
            alerta.setContentText("El documento ya está registrado");
            alerta.showAndWait();

            return;
        }

        if (servicioUsuarios.buscarPorCorreo(correo) != null){

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Registro");
            alerta.setHeaderText(null);
            alerta.setContentText("El correo electrónico ya está registrado");
            alerta.showAndWait();

            return;
        }

        if (!correo.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Registro");
            alerta.setHeaderText(null);
            alerta.setContentText("Ingrese un correo electrónico válido");
            alerta.showAndWait();

            return;
        }

        if (!telefono.matches("\\d+")){

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Registro");
            alerta.setHeaderText(null);
            alerta.setContentText("El teléfono solo debe contener números");
            alerta.showAndWait();

            return;
        }

        if (telefono.length() != 10){

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Registro");
            alerta.setHeaderText(null);
            alerta.setContentText("El teléfono debe contener exactamente 10 números");
            alerta.showAndWait();

            return;
        }

        TarjetaVirtual tarjetaVirtual = new TarjetaVirtual("TX-" + documento, 0 , true);

        CuentaPuntos cuentaPuntos = new CuentaPuntos(0);

        Cliente cliente = new Cliente(documento, nombre, correo, telefono, contrasenia, tarjetaVirtual, cuentaPuntos);

        servicioUsuarios.agregarUsuario(cliente);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Registro");
        alerta.setHeaderText(null);
        alerta.setContentText("Cliente registrado exitosamente");
        alerta.showAndWait();

        System.out.println("Cliente registrado correctamente: " + nombre);


    }
}
