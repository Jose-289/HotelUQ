package edu.hoteluq.controlador;

import edu.hoteluq.modelo.Servicio;
import edu.hoteluq.servicio.ServicioServiciosAdicionales;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorGestionServicios {

    @FXML private TableView<Servicio> tablaServicios;
    @FXML private TableColumn<Servicio, String> colId;
    @FXML private TableColumn<Servicio, String> colNombre;
    @FXML private TableColumn<Servicio, String> colDescripcion;
    @FXML private TableColumn<Servicio, Number> colPrecio;
    @FXML private TableColumn<Servicio, Boolean> colDisponible;

    @FXML private TextField campoId;
    @FXML private TextField campoNombre;
    @FXML private TextField campoDescripcion;
    @FXML private TextField campoPrecio;
    @FXML private CheckBox checkDisponible;

    private final ServicioServiciosAdicionales servicioAdicionales = ServicioServiciosAdicionales.getInstancia();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        colDescripcion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));
        colPrecio.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrecio()));
        colDisponible.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isDisponible()));

        cargarTabla();
    }

    private void cargarTabla() {
        tablaServicios.setItems(FXCollections.observableArrayList(servicioAdicionales.obtenerServiciosDisponibles()));
    }

    @FXML
    public void registrarServicio() {
        try {
            String id = campoId.getText().trim();
            String nombre = campoNombre.getText().trim();
            String descripcion = campoDescripcion.getText().trim();
            double precio = Double.parseDouble(campoPrecio.getText().trim());
            boolean disponible = checkDisponible.isSelected();

            if (id.isEmpty() || nombre.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "El código y el nombre son obligatorios.");
                return;
            }

            servicioAdicionales.registrarServicio(id, nombre, descripcion, precio, disponible);
            cargarTabla();
            campoId.clear();
            campoNombre.clear();
            campoDescripcion.clear();
            campoPrecio.clear();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Servicio adicional registrado correctamente.");
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "El precio debe ser un valor numérico válido.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    public void volverMenu(ActionEvent evento) throws IOException {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/edu/hoteluq/Vistas/vistaMenuAdministrador.fxml"));
        Scene escena = new Scene(cargador.load());
        ControladorMenuAdministrador controlador = cargador.getController();
        controlador.mostrarBienvenida("Administrador");

        Stage ventana = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        ventana.setScene(escena);
        ventana.setTitle("HotelUQ - Menú del administrador");
        ventana.show();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}