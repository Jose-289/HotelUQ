package edu.hoteluq.controlador;

import edu.hoteluq.modelo.Promocion;
import edu.hoteluq.patron.comportamental.strategy.DescuentoMontoFijoStrategy;
import edu.hoteluq.patron.comportamental.strategy.DescuentoPorcentajeStrategy;
import edu.hoteluq.patron.comportamental.strategy.EstrategiaDescuento;
import edu.hoteluq.servicio.ServicioPromociones;
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
import java.time.LocalDate;

public class ControladorGestionPromociones {

    @FXML private TextField campoCodigo;
    @FXML private TextField campoDescripcion;
    @FXML private ComboBox<String> comboTipoEstrategia;
    @FXML private TextField campoValorDescuento;
    @FXML private TextField campoCondicionMinima;
    @FXML private DatePicker dateInicio;
    @FXML private DatePicker dateFin;

    @FXML private TableView<Promocion> tablaPromociones;
    @FXML private TableColumn<Promocion, String> colCodigo;
    @FXML private TableColumn<Promocion, String> colDescripcion;
    @FXML private TableColumn<Promocion, String> colEstrategia;
    @FXML private TableColumn<Promocion, String> colVigencia;
    @FXML private TableColumn<Promocion, String> colEstado;

    private final ServicioPromociones servicioPromociones = ServicioPromociones.getInstancia();

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodigo()));
        colDescripcion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));
        colEstrategia.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstrategia().getNombreEstrategia()));
        colVigencia.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getFechaInicio() + " al " + data.getValue().getFechaFin()
        ));
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().estaVigente() ? "Vigente" : "Inactiva/Vencida"));

        comboTipoEstrategia.setItems(FXCollections.observableArrayList("Porcentaje (%)", "Monto Fijo ($)"));
        cargarTabla();
    }

    private void cargarTabla() {
        tablaPromociones.setItems(FXCollections.observableArrayList(servicioPromociones.obtenerPromociones()));
        tablaPromociones.refresh();
    }

    @FXML
    public void registrarPromocion() {
        try {
            String codigo = campoCodigo.getText().trim().toUpperCase();
            String descripcion = campoDescripcion.getText().trim();
            String tipo = comboTipoEstrategia.getValue();
            LocalDate inicio = dateInicio.getValue();
            LocalDate fin = dateFin.getValue();

            if (codigo.isEmpty() || descripcion.isEmpty() || tipo == null || inicio == null || fin == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Complete todos los campos obligatorios.");
                return;
            }

            double valor = Double.parseDouble(campoValorDescuento.getText().trim());
            double condicion = Double.parseDouble(campoCondicionMinima.getText().trim());

            EstrategiaDescuento estrategia = tipo.startsWith("Porcentaje")
                    ? new DescuentoPorcentajeStrategy(valor, (int) condicion)
                    : new DescuentoMontoFijoStrategy(valor, condicion);

            servicioPromociones.registrarPromocion(codigo, descripcion, inicio, fin, estrategia, true);
            cargarTabla();
            limpiarCampos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Promoción registrada correctamente.");
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error numérico", "Verifique que el valor y la condición sean números válidos.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    public void eliminarPromocion() {
        Promocion seleccionada = tablaPromociones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Seleccione una promoción de la tabla.");
            return;
        }
        servicioPromociones.eliminarPromocion(seleccionada);
        cargarTabla();
    }

    private void limpiarCampos() {
        campoCodigo.clear();
        campoDescripcion.clear();
        campoValorDescuento.clear();
        campoCondicionMinima.clear();
        dateInicio.setValue(null);
        dateFin.setValue(null);
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