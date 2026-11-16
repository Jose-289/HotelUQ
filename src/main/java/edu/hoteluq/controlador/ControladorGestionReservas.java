package edu.hoteluq.controlador;

import edu.hoteluq.modelo.*;
import edu.hoteluq.servicio.ServicioHabitaciones;
import edu.hoteluq.servicio.ServicioReservas;
import edu.hoteluq.servicio.ServicioUsuarios;
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
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ControladorGestionReservas {

    @FXML private ComboBox<Cliente> comboClientes;
    @FXML private ComboBox<Habitacion> comboHabitaciones;
    @FXML private DatePicker dateEntrada;
    @FXML private DatePicker dateSalida;

    @FXML private TableView<Reserva> tablaReservas;
    @FXML private TableColumn<Reserva, String> colId;
    @FXML private TableColumn<Reserva, String> colCliente;
    @FXML private TableColumn<Reserva, String> colHabitacion;
    @FXML private TableColumn<Reserva, String> colEntrada;
    @FXML private TableColumn<Reserva, String> colSalida;
    @FXML private TableColumn<Reserva, Number> colTotal;
    @FXML private TableColumn<Reserva, String> colEstado;

    private final ServicioReservas servicioReservas = ServicioReservas.getInstancia();
    private final ServicioHabitaciones servicioHabitaciones = ServicioHabitaciones.getInstancia();
    private final ServicioUsuarios servicioUsuarios = ServicioUsuarios.getInstancia();

    @FXML
    public void initialize() {
        configurarTabla();
        cargarCombos();
        cargarReservas();
    }

    private void configurarTabla() {
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        colCliente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCliente().getNombre()));
        colHabitacion.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getHabitacion().getNumero() + " (" + data.getValue().getHabitacion().getNombreTipo() + ")"
        ));
        colEntrada.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFechaEntrada().toLocalDate().toString()));
        colSalida.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFechaSalida().toLocalDate().toString()));
        colTotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorTotal()));
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().name()));
    }

    private void cargarCombos() {
        List<Cliente> clientes = new ArrayList<>();
        for (Usuario u : servicioUsuarios.obtenerUsuarios()) {
            if (u instanceof Cliente) {
                clientes.add((Cliente) u);
            }
        }
        comboClientes.setItems(FXCollections.observableArrayList(clientes));
        comboClientes.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente c) {
                return c != null ? c.getNombre() + " (Saldo: $" + c.getTarjetaVirtual().getSaldo() + ")" : "";
            }
            @Override
            public Cliente fromString(String s) { return null; }
        });

        comboHabitaciones.setItems(FXCollections.observableArrayList(servicioHabitaciones.obtenerTodasHabitaciones()));
        comboHabitaciones.setConverter(new StringConverter<>() {
            @Override
            public String toString(Habitacion h) {
                return h != null ? "Hab. " + h.getNumero() + " - " + h.getNombreTipo() + " ($" + h.getTarifaNoche() + ")" : "";
            }
            @Override
            public Habitacion fromString(String s) { return null; }
        });
    }

    private void cargarReservas() {
        tablaReservas.setItems(FXCollections.observableArrayList(servicioReservas.obtenerTodasReservas()));
        tablaReservas.refresh();
    }

    @FXML
    public void crearReserva() {
        Cliente cliente = comboClientes.getValue();
        Habitacion habitacion = comboHabitaciones.getValue();
        LocalDate entrada = dateEntrada.getValue();
        LocalDate salida = dateSalida.getValue();

        if (cliente == null || habitacion == null || entrada == null || salida == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos", "Seleccione cliente, habitación, fecha de entrada y fecha de salida.");
            return;
        }

        try {
            LocalDateTime fechaHoraEntrada = LocalDateTime.of(entrada, LocalTime.of(15, 0));
            LocalDateTime fechaHoraSalida = LocalDateTime.of(salida, LocalTime.of(12, 0));

            Reserva reserva = servicioReservas.crearYConfirmarReserva(
                    cliente, habitacion, fechaHoraEntrada, fechaHoraSalida, new ArrayList<>(), null
            );

            cargarReservas();
            cargarCombos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Reserva Confirmada",
                    "Reserva " + reserva.getId() + " creada y pagada por $" + reserva.getValorTotal());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo realizar la reserva", e.getMessage());
        }
    }

    @FXML
    public void cancelarReservaSeleccionada() {
        Reserva seleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Seleccione una reserva de la tabla para cancelar.");
            return;
        }

        try {
            servicioReservas.cancelarReservaPorHotel(seleccionada);
            cargarReservas();
            cargarCombos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Reserva Cancelada", "La reserva fue cancelada y reembolsada al 100% a la tarjeta del cliente.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cancelar", e.getMessage());
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