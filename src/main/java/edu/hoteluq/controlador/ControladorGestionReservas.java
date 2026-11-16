package edu.hoteluq.controlador;

import edu.hoteluq.modelo.*;
import edu.hoteluq.servicio.*;
import javafx.beans.property.SimpleDoubleProperty;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControladorGestionReservas {

    @FXML private ComboBox<Cliente> comboClientes;
    @FXML private ComboBox<Habitacion> comboHabitaciones;
    @FXML private DatePicker dateEntrada;
    @FXML private DatePicker dateSalida;

    @FXML private ComboBox<Servicio> comboServicios;
    @FXML private ListView<Servicio> listaServiciosSeleccionados;
    @FXML private ComboBox<Promocion> comboPromociones;
    @FXML private Label lblResumenEstimado;

    @FXML private TableView<Reserva> tablaReservas;
    @FXML private TableColumn<Reserva, String> colId;
    @FXML private TableColumn<Reserva, String> colCliente;
    @FXML private TableColumn<Reserva, String> colHabitacion;
    @FXML private TableColumn<Reserva, String> colFechas;
    @FXML private TableColumn<Reserva, String> colServicios;
    @FXML private TableColumn<Reserva, String> colPromocion;
    @FXML private TableColumn<Reserva, Number> colTotal;
    @FXML private TableColumn<Reserva, String> colEstado;

    private final ServicioReservas servicioReservas = ServicioReservas.getInstancia();
    private final ServicioHabitaciones servicioHabitaciones = ServicioHabitaciones.getInstancia();
    private final ServicioUsuarios servicioUsuarios = ServicioUsuarios.getInstancia();
    private final ServicioServiciosAdicionales servicioAdicionales = ServicioServiciosAdicionales.getInstancia();
    private final ServicioPromociones servicioPromociones = ServicioPromociones.getInstancia();

    private final ObservableList<Servicio> serviciosAgregados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabla();
        configurarListaServicios();
        cargarCombos();
        configurarListenersCalculo();
        cargarReservas();
    }

    private void configurarTabla() {
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        colCliente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCliente().getNombre()));
        colHabitacion.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getHabitacion().getNumero() + " (" + data.getValue().getHabitacion().getNombreTipo() + ")"
        ));
        colFechas.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getFechaEntrada().toLocalDate() + " → " + data.getValue().getFechaSalida().toLocalDate()
        ));
        colServicios.setCellValueFactory(data -> {
            List<Servicio> srvs = data.getValue().getServicios();
            String texto = srvs.isEmpty() ? "Ninguno" : srvs.stream().map(Servicio::getNombre).collect(Collectors.joining(", "));
            return new SimpleStringProperty(texto);
        });
        colPromocion.setCellValueFactory(data -> {
            Promocion p = data.getValue().getPromocion();
            return new SimpleStringProperty(p != null ? p.getCodigo() : "N/A");
        });
        colTotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorTotal()));
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().name()));
    }

    private void configurarListaServicios() {
        listaServiciosSeleccionados.setItems(serviciosAgregados);
        listaServiciosSeleccionados.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Servicio item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombre() + " (+$" + item.getPrecio() + ")");
            }
        });
    }

    private void cargarCombos() {
        // Clientes
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

        // Habitaciones
        comboHabitaciones.setItems(FXCollections.observableArrayList(servicioHabitaciones.obtenerTodasHabitaciones()));
        comboHabitaciones.setConverter(new StringConverter<>() {
            @Override
            public String toString(Habitacion h) {
                return h != null ? "Hab. " + h.getNumero() + " - " + h.getNombreTipo() + " ($" + h.getTarifaNoche() + "/noche)" : "";
            }
            @Override
            public Habitacion fromString(String s) { return null; }
        });

        // Servicios Adicionales disponibles
        List<Servicio> disponibles = servicioAdicionales.obtenerServiciosDisponibles().stream()
                .filter(Servicio::isDisponible)
                .collect(Collectors.toList());
        comboServicios.setItems(FXCollections.observableArrayList(disponibles));
        comboServicios.setConverter(new StringConverter<>() {
            @Override
            public String toString(Servicio s) {
                return s != null ? s.getNombre() + " ($" + s.getPrecio() + ")" : "";
            }
            @Override
            public Servicio fromString(String s) { return null; }
        });

        // Promociones vigentes (incluye opción null para "Sin promoción")
        List<Promocion> vigentes = new ArrayList<>();
        vigentes.add(null);
        vigentes.addAll(servicioPromociones.obtenerPromociones().stream()
                .filter(Promocion::estaVigente)
                .collect(Collectors.toList()));
        comboPromociones.setItems(FXCollections.observableArrayList(vigentes));
        comboPromociones.setConverter(new StringConverter<>() {
            @Override
            public String toString(Promocion p) {
                return p != null ? p.getCodigo() + " - " + p.getDescripcion() : "Sin promoción";
            }
            @Override
            public Promocion fromString(String s) { return null; }
        });
    }

    private void configurarListenersCalculo() {
        comboHabitaciones.valueProperty().addListener((obs, o, n) -> actualizarEstimado());
        dateEntrada.valueProperty().addListener((obs, o, n) -> actualizarEstimado());
        dateSalida.valueProperty().addListener((obs, o, n) -> actualizarEstimado());
        comboPromociones.valueProperty().addListener((obs, o, n) -> actualizarEstimado());
    }

    @FXML
    public void agregarServicioALista() {
        Servicio seleccionado = comboServicios.getValue();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Seleccione un servicio adicional para añadir.");
            return;
        }
        serviciosAgregados.add(seleccionado);
        actualizarEstimado();
    }

    @FXML
    public void limpiarServiciosSeleccionados() {
        serviciosAgregados.clear();
        actualizarEstimado();
    }

    private void actualizarEstimado() {
        Habitacion habitacion = comboHabitaciones.getValue();
        LocalDate entrada = dateEntrada.getValue();
        LocalDate salida = dateSalida.getValue();

        if (habitacion == null || entrada == null || salida == null || !salida.isAfter(entrada)) {
            lblResumenEstimado.setText("Total estimado: $0.0");
            return;
        }

        LocalDateTime fechaHoraEntrada = LocalDateTime.of(entrada, LocalTime.of(15, 0));
        LocalDateTime fechaHoraSalida = LocalDateTime.of(salida, LocalTime.of(12, 0));
        Promocion promo = comboPromociones.getValue();

        ServicioReservas.ResultadoCotizacion cotizacion = servicioReservas.cotizarReserva(
                habitacion, fechaHoraEntrada, fechaHoraSalida, serviciosAgregados, promo
        );

        String notaPromo = "";
        if (promo != null) {
            notaPromo = cotizacion.promoAplicada()
                    ? String.format(" (Desc. aplicado: -$%.1f)", cotizacion.descuento())
                    : " (No cumple condición de promo)";
        }

        lblResumenEstimado.setText(String.format("Total estimado: $%.1f%s", cotizacion.totalFinal(), notaPromo));
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
        Promocion promoSeleccionada = comboPromociones.getValue();

        if (cliente == null || habitacion == null || entrada == null || salida == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos", "Seleccione cliente, habitación, fecha de entrada y fecha de salida.");
            return;
        }

        try {
            LocalDateTime fechaHoraEntrada = LocalDateTime.of(entrada, LocalTime.of(15, 0));
            LocalDateTime fechaHoraSalida = LocalDateTime.of(salida, LocalTime.of(12, 0));

            ServicioReservas.ResultadoCotizacion cotizacion = servicioReservas.cotizarReserva(
                    habitacion, fechaHoraEntrada, fechaHoraSalida, serviciosAgregados, promoSeleccionada
            );

            if (promoSeleccionada != null && !cotizacion.promoAplicada()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Promoción no aplicable",
                        "La promoción '" + promoSeleccionada.getCodigo() + "' no cumple los requisitos ("
                                + promoSeleccionada.getEstrategia().getNombreEstrategia() + ").");
                return;
            }

            Reserva reserva = servicioReservas.crearYConfirmarReserva(
                    cliente,
                    habitacion,
                    fechaHoraEntrada,
                    fechaHoraSalida,
                    new ArrayList<>(serviciosAgregados),
                    promoSeleccionada
            );

            serviciosAgregados.clear();
            comboPromociones.setValue(null);
            cargarReservas();
            cargarCombos();
            actualizarEstimado();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Reserva Confirmada",
                    "Código: " + reserva.getId() + "\n"
                            + "Detalle: " + cotizacion.descripcion() + "\n"
                            + "Total pagado: $" + reserva.getValorTotal());
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