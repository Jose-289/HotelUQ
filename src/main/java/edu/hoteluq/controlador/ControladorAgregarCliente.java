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

public class ControladorAgregarCliente {

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

    @FXML
    public void agregarCliente(){

        String documento = campoDocumento.getText().trim();
        String nombre = campoNombre.getText().trim();
        String correo = campoCorreo.getText().trim();
        String telefono = campoTelefono.getText().trim();
        String contrasenia = campoContrasenia.getText().trim();

        if (documento.isEmpty() || nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contrasenia.isEmpty()){

            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        if (servicioUsuarios.buscarPorId(documento) != null){

            mostrarAlerta("El cliente ya se encuentra registrado.");
            return;
        }

        if (!correo.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){

            mostrarAlerta("Ingrese un correo electrónica válido");
            return;
        }

        if (!telefono.matches("\\d+")){

            mostrarAlerta("El teléfono solo debe contener números");
            return;
        }

        if (telefono.length() != 10){

            mostrarAlerta("El tele¿éfono debe contener exactamente 10 números");
            return;
        }

        if (contrasenia.length() < 6){

            mostrarAlerta("La contraseña debe tener mínimo 6 caracteres");
            return;
        }

        TarjetaVirtual tarjetaVirtual = new TarjetaVirtual("TV-" + documento, 0, true);

        CuentaPuntos cuentaPuntos = new CuentaPuntos(0);

        Cliente cliente = new Cliente(documento, nombre, correo, telefono, contrasenia, tarjetaVirtual, cuentaPuntos);

        servicioUsuarios.agregarUsuario(cliente);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Agregar cliente");
        alerta.setHeaderText(null);
        alerta.setContentText("Cliente agregado correctamente.");
        alerta.showAndWait();

        limpiarCampos();
    }

    @FXML
    public void volverGestionClientes(ActionEvent evento) throws IOException{

        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/edu/hoteluq/Vistas/vistaGestionClientes.fxml"));

        Scene escena = new Scene(cargador.load());

        Stage ventana = (Stage) ((Node) evento.getSource()).getScene().getWindow();

        ventana.setScene(escena);
        ventana.setTitle("HotelUQ - Gestión de clientes");
        ventana.show();
    }

    private void limpiarCampos(){
        campoDocumento.clear();
        campoNombre.clear();
        campoCorreo.clear();
        campoTelefono.clear();
        campoContrasenia.clear();
    }

    private void mostrarAlerta(String mensaje){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Agregar cliente");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
