package edu.hoteluq.controlador;

import edu.hoteluq.modelo.EstadoHabitacion;
import edu.hoteluq.modelo.Habitacion;
import edu.hoteluq.patron.creacional.factory.FabricaHabitacion;
import edu.hoteluq.servicio.ServicioHabitaciones;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;

public class ControladorGestionHabitaciones {

    @FXML
    private TableView<Habitacion> tablaHabitaciones;
    @FXML
    private TableColumn<Habitacion, String> columnaNumero;
    @FXML
    private TableColumn<Habitacion, String> columnaTipo;
    @FXML
    private TableColumn<Habitacion, Number> columnaCapacidad;
    @FXML
    private TableColumn<Habitacion, Number> columnaTarifa;
    @FXML
    private TableColumn<Habitacion, String> columnaEstado;

    @FXML
    private TextField campoNumero;
    @FXML
    private ComboBox<FabricaHabitacion> comboFabricas;
    @FXML
    private ComboBox<EstadoHabitacion> comboEstado;

    private final ServicioHabitaciones servicioHabitaciones = ServicioHabitaciones.getInstancia();

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla usando polimorfismo
        columnaNumero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumero()));
        columnaTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreTipo()));
        columnaCapacidad.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCapacidad()));
        columnaTarifa.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTarifaNoche()));
        columnaEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado().name()));

        // Cargar fábricas en el ComboBox de forma polimórfica (sin switch/if)
        comboFabricas.setItems(FXCollections.observableArrayList(servicioHabitaciones.obtenerFabricasDisponibles()));
        comboFabricas.setConverter(new StringConverter<FabricaHabitacion>() {
            @Override
            public String toString(FabricaHabitacion fabrica) {
                return fabrica != null ? fabrica.getNombreTipo() : "";
            }

            @Override
            public FabricaHabitacion fromString(String string) {
                return null;
            }
        });

        // Cargar estados posibles
        comboEstado.setItems(FXCollections.observableArrayList(EstadoHabitacion.values()));
        comboEstado.setValue(EstadoHabitacion.DISPONIBLE);

        cargarHabitaciones();
    }

    private void cargarHabitaciones() {
        ObservableList<Habitacion> lista = FXCollections.observableArrayList(servicioHabitaciones.obtenerTodasHabitaciones());
        tablaHabitaciones.setItems(lista);
    }

    @FXML
    public void registrarHabitacion() {
        String numero = campoNumero.getText().trim();
        FabricaHabitacion fabricaSeleccionada = comboFabricas.getValue();
        EstadoHabitacion estadoSeleccionado = comboEstado.getValue();

        if (numero.isEmpty() || fabricaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Ingrese el número de habitación y seleccione un tipo.");
            return;
        }

        try {
            // Se pasa directamente la fábrica al servicio (Cumple con OCP y DIP)
            servicioHabitaciones.registrarHabitacion(numero, fabricaSeleccionada, estadoSeleccionado);
            cargarHabitaciones();
            campoNumero.clear();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Habitación registrada correctamente.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    public void eliminarHabitacion() {
        Habitacion seleccionada = tablaHabitaciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Seleccione una habitación de la tabla para eliminar.");
            return;
        }

        servicioHabitaciones.eliminarHabitacion(seleccionada);
        cargarHabitaciones();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminado", "Habitación eliminada con éxito.");
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