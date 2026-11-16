package edu.hoteluq.controlador;

import edu.hoteluq.modelo.*;
import edu.hoteluq.patron.comportamental.state.ContextoFidelizacion;
import edu.hoteluq.patron.estructural.facade.FachadaClienteHotel;
import edu.hoteluq.patron.estructural.proxy.OperacionBilletera;
import edu.hoteluq.servicio.ServicioReservas;
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

public class ControladorMenuCliente {

    @FXML private Label lblBienvenidaCliente;
    @FXML private Label lblResumenHeader;

    // Pestaña Reservar
    @FXML private ComboBox<Habitacion> comboHabitaciones;
    @FXML private DatePicker dateEntrada;
    @FXML private DatePicker dateSalida;
    @FXML private ComboBox<Servicio> comboServicios;
    @FXML private ListView<Servicio> listaServicios;
    @FXML private ComboBox<Promocion> comboPromociones;
    @FXML private Label lblCotizacion;

    // Pestaña Mis Reservas
    @FXML private TableView<Reserva> tablaMisReservas;
    @FXML private TableColumn<Reserva, String> colResId;
    @FXML private TableColumn<Reserva, String> colResHab;
    @FXML private TableColumn<Reserva, String> colResFechas;
    @FXML private TableColumn<Reserva, String> colResServicios;
    @FXML private TableColumn<Reserva, Number> colResTotal;
    @FXML private TableColumn<Reserva, String> colResEstado;

    // Pestaña Billetera y Puntos
    @FXML private Label lblSaldoTarjeta;
    @FXML private TextField campoMontoRecarga;
    @FXML private ListView<String> listaAuditoriaBilletera;
    @FXML private Label lblNivelFidelizacion;
    @FXML private Label lblPuntosDisponibles;
    @FXML private Label lblBeneficioNivel;
    @FXML private TextField campoPuntosCanje;

    // Pestaña Notificaciones
    @FXML private ListView<String> listaNotificaciones;

    private final FachadaClienteHotel fachada = FachadaClienteHotel.getInstancia();
    private final ObservableList<Servicio> serviciosSeleccionados = FXCollections.observableArrayList();
    private Cliente clienteActual;

    @FXML
    public void initialize() {
        configurarTablaReservas();
        listaServicios.setItems(serviciosSeleccionados);
        listaServicios.setCellFactory(p -> new ListCell<>() {
            @Override
            protected void updateItem(Servicio item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombre() + " (+$" + item.getPrecio() + ")");
            }
        });

        comboHabitaciones.valueProperty().addListener((o, a, b) -> actualizarCotizacion());
        dateEntrada.valueProperty().addListener((o, a, b) -> actualizarCotizacion());
        dateSalida.valueProperty().addListener((o, a, b) -> actualizarCotizacion());
        comboPromociones.valueProperty().addListener((o, a, b) -> actualizarCotizacion());
    }

    public void inicializarCliente(Cliente cliente) {
        this.clienteActual = cliente;
        lblBienvenidaCliente.setText("Bienvenido, " + cliente.getNombre());
        refrescarTodo();
    }

    private void configurarTablaReservas() {
        colResId.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getId()));
        colResHab.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getHabitacion().getNumero() + " (" + d.getValue().getHabitacion().getNombreTipo() + ")"
        ));
        colResFechas.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().getFechaEntrada().toLocalDate() + " → " + d.getValue().getFechaSalida().toLocalDate()
        ));
        colResServicios.setCellValueFactory(d -> {
            List<Servicio> s = d.getValue().getServicios();
            return new SimpleStringProperty(s.isEmpty() ? "Ninguno" : s.stream().map(Servicio::getNombre).collect(Collectors.joining(", ")));
        });
        colResTotal.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getValorTotal()));
        colResEstado.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEstado().name()));
    }

    private void refrescarTodo() {
        if (clienteActual == null) return;

        // 1. Billetera (Proxy) y Fidelización (State)
        OperacionBilletera billetera = fachada.obtenerBilletera(clienteActual);
        ContextoFidelizacion fidelizacion = fachada.obtenerEstadoFidelizacion(clienteActual);

        double saldo = billetera.consultarSaldo();
        int puntos = fidelizacion.getPuntosAcumulados();
        String nivel = fidelizacion.getEstado().getNombreNivel();

        lblResumenHeader.setText(String.format("Saldo: $%.0f | Nivel: %s (%d pts)", saldo, nivel, puntos));
        lblSaldoTarjeta.setText(String.format("Saldo Disponible: $%.2f", saldo));
        listaAuditoriaBilletera.setItems(FXCollections.observableArrayList(billetera.obtenerHistorialAuditoria()));

        lblNivelFidelizacion.setText("Nivel Actual: " + nivel);
        lblPuntosDisponibles.setText("Puntos Acumulados: " + puntos + " pts");
        lblBeneficioNivel.setText(String.format("Multiplicador: %.2fx | Bono canje: +%.0f%%",
                fidelizacion.getEstado().getMultiplicadorPuntos(),
                fidelizacion.getEstado().getPorcentajeBonoCanje() * 100));

        // 2. Combos de Reserva
        comboHabitaciones.setItems(FXCollections.observableArrayList(fachada.obtenerHabitacionesDisponibles()));
        comboHabitaciones.setConverter(new StringConverter<>() {
            @Override public String toString(Habitacion h) {
                return h != null ? "Hab. " + h.getNumero() + " - " + h.getNombreTipo() + " ($" + h.getTarifaNoche() + "/noche)" : "";
            }
            @Override public Habitacion fromString(String s) { return null; }
        });

        comboServicios.setItems(FXCollections.observableArrayList(fachada.obtenerServiciosDisponibles()));
        comboServicios.setConverter(new StringConverter<>() {
            @Override public String toString(Servicio s) { return s != null ? s.getNombre() + " ($" + s.getPrecio() + ")" : ""; }
            @Override public Servicio fromString(String s) { return null; }
        });

        List<Promocion> promos = new ArrayList<>();
        promos.add(null);
        promos.addAll(fachada.obtenerPromocionesVigentes());
        comboPromociones.setItems(FXCollections.observableArrayList(promos));
        comboPromociones.setConverter(new StringConverter<>() {
            @Override public String toString(Promocion p) { return p != null ? p.getCodigo() + " - " + p.getDescripcion() : "Sin promoción"; }
            @Override public Promocion fromString(String s) { return null; }
        });

        // 3. Tabla de Reservas y Notificaciones (Observer)
        tablaMisReservas.setItems(FXCollections.observableArrayList(fachada.obtenerMisReservas(clienteActual)));
        listaNotificaciones.setItems(FXCollections.observableArrayList(fachada.obtenerNotificaciones(clienteActual)));
    }

    @FXML
    public void agregarServicio() {
        Servicio s = comboServicios.getValue();
        if (s != null) {
            serviciosSeleccionados.add(s);
            actualizarCotizacion();
        }
    }

    @FXML
    public void limpiarServicios() {
        serviciosSeleccionados.clear();
        actualizarCotizacion();
    }

    private void actualizarCotizacion() {
        Habitacion h = comboHabitaciones.getValue();
        LocalDate e = dateEntrada.getValue();
        LocalDate s = dateSalida.getValue();
        if (h == null || e == null || s == null || !s.isAfter(e)) {
            lblCotizacion.setText("Total estimado: $0.0");
            return;
        }
        ServicioReservas.ResultadoCotizacion cot = fachada.cotizarEstadia(
                h, LocalDateTime.of(e, LocalTime.of(15, 0)), LocalDateTime.of(s, LocalTime.of(12, 0)),
                serviciosSeleccionados, comboPromociones.getValue()
        );
        lblCotizacion.setText(String.format("Total estimado: $%.1f%s",
                cot.totalFinal(), cot.promoAplicada() ? " (Descuento aplicado)" : ""));
    }

    @FXML
    public void confirmarReserva() {
        Habitacion h = comboHabitaciones.getValue();
        LocalDate e = dateEntrada.getValue();
        LocalDate s = dateSalida.getValue();
        if (h == null || e == null || s == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Seleccione habitación y fechas.");
            return;
        }
        try {
            Reserva r = fachada.reservarEstadiaCompleta(
                    clienteActual, h,
                    LocalDateTime.of(e, LocalTime.of(15, 0)),
                    LocalDateTime.of(s, LocalTime.of(12, 0)),
                    new ArrayList<>(serviciosSeleccionados),
                    comboPromociones.getValue()
            );
            serviciosSeleccionados.clear();
            comboPromociones.setValue(null);
            refrescarTodo();
            actualizarCotizacion();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Reserva Exitosa",
                    "Reserva " + r.getId() + " confirmada. Se han acreditado puntos en tu cuenta.");
        } catch (Exception ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo reservar", ex.getMessage());
        }
    }

    @FXML
    public void recargarTarjeta() {
        try {
            double monto = Double.parseDouble(campoMontoRecarga.getText().trim());
            fachada.recargarBilletera(clienteActual, monto);
            campoMontoRecarga.clear();
            refrescarTodo();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Recarga Exitosa", "Saldo actualizado en tu tarjeta virtual.");
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Valor inválido", "Ingrese un monto numérico válido.");
        } catch (Exception ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Recarga rechazada", ex.getMessage());
        }
    }

    @FXML
    public void canjearPuntos() {
        try {
            int pts = Integer.parseInt(campoPuntosCanje.getText().trim());
            double saldoGanado = fachada.canjearPuntos(clienteActual, pts);
            campoPuntosCanje.clear();
            refrescarTodo();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Canje Exitoso",
                    String.format("Canjeaste %d puntos por $%.0f en tu tarjeta virtual.", pts, saldoGanado));
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Valor inválido", "Ingrese una cantidad entera de puntos.");
        } catch (Exception ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo canjear", ex.getMessage());
        }
    }

    @FXML
    public void cerrarSesion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/hoteluq/Vistas/vistaInicioSesion.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("HotelUQ - Inicio de Sesión");
        stage.show();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    public void mostrarBienvenida(String nombreUsuario) {
        lblBienvenidaCliente.setText("Bienvenido, " + nombreUsuario);
        if (clienteActual == null) {
            for (Usuario u : edu.hoteluq.servicio.ServicioUsuarios.getInstancia().obtenerUsuarios()) {
                if (u instanceof Cliente && u.getNombre().equalsIgnoreCase(nombreUsuario)) {
                    inicializarCliente((Cliente) u);
                    break;
                }
            }
        }
    }
}