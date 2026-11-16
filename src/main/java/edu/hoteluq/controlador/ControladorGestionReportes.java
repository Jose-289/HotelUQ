package edu.hoteluq.controlador;

import edu.hoteluq.modelo.DatosReporte;
import edu.hoteluq.modelo.Reserva;
import edu.hoteluq.patron.estructural.adapter.AdaptadorReportePdf;
import edu.hoteluq.patron.estructural.adapter.AdaptadorReporteTxt;
import edu.hoteluq.patron.estructural.adapter.GeneradorReporte;
import edu.hoteluq.servicio.ServicioReportes;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ControladorGestionReportes {

    @FXML private Label lblKpiHabitaciones;
    @FXML private Label lblKpiClientes;
    @FXML private Label lblKpiReservas;
    @FXML private Label lblKpiIngresos;
    @FXML private ComboBox<GeneradorReporte> comboFormatos;

    @FXML private TableView<Reserva> tablaVistaPrevia;
    @FXML private TableColumn<Reserva, String> colId;
    @FXML private TableColumn<Reserva, String> colCliente;
    @FXML private TableColumn<Reserva, String> colHabitacion;
    @FXML private TableColumn<Reserva, Number> colTotal;
    @FXML private TableColumn<Reserva, String> colEstado;

    private final ServicioReportes servicioReportes = ServicioReportes.getInstancia();

    @FXML
    public void initialize() {
        configurarTabla();
        configurarComboFormatos();
        cargarDatos();
    }

    private void configurarTabla() {
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        colCliente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCliente().getNombre()));
        colHabitacion.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getHabitacion().getNumero() + " - " + data.getValue().getHabitacion().getNombreTipo()
        ));
        colTotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorTotal()));
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().name()));
    }

    private void configurarComboFormatos() {
        comboFormatos.setItems(FXCollections.observableArrayList(
                new AdaptadorReporteTxt(),
                new AdaptadorReportePdf()
        ));
        comboFormatos.setConverter(new StringConverter<>() {
            @Override
            public String toString(GeneradorReporte gen) {
                return gen != null ? gen.getDescripcionFormato() : "";
            }
            @Override
            public GeneradorReporte fromString(String s) { return null; }
        });
        comboFormatos.getSelectionModel().selectFirst();
    }

    private void cargarDatos() {
        DatosReporte datos = servicioReportes.recopilarDatosActuales();
        lblKpiHabitaciones.setText(datos.getHabitacionesDisponibles() + " / " + datos.getTotalHabitaciones());
        lblKpiClientes.setText(String.valueOf(datos.getTotalClientes()));
        lblKpiReservas.setText(String.valueOf(datos.getTotalReservasConfirmadas()));
        lblKpiIngresos.setText(String.format("$%.1f", datos.getIngresosTotales()));

        tablaVistaPrevia.setItems(FXCollections.observableArrayList(datos.getReservas()));
    }

    @FXML
    public void exportarReporte(ActionEvent evento) {
        GeneradorReporte adaptador = comboFormatos.getValue();
        if (adaptador == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Formato requerido", "Seleccione un formato de exportación.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte HotelUQ");
        fileChooser.setInitialFileName("Reporte_HotelUQ_" + LocalDate.now() + "." + adaptador.getExtension());
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(adaptador.getDescripcionFormato(), "*." + adaptador.getExtension())
        );

        Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        File archivo = fileChooser.showSaveDialog(stage);

        if (archivo != null) {
            try {
                servicioReportes.exportarReporte(adaptador, archivo);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Reporte Generado",
                        "El reporte se guardó exitosamente en:\n" + archivo.getAbsolutePath());
            } catch (IOException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de exportación", "No se pudo escribir el archivo: " + e.getMessage());
            }
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