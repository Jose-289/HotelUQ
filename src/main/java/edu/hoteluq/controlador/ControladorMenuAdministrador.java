package edu.hoteluq.controlador;

import edu.hoteluq.servicio.ServicioHabitaciones;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorMenuAdministrador {

    @FXML
    private Label textoBienvenida;

    @FXML
    private Label lblOcupacion;

    @FXML
    private Label lblDisponibles;

    private final ServicioHabitaciones servicioHabitaciones = ServicioHabitaciones.getInstancia();

    public void mostrarBienvenida(String nombre){
        textoBienvenida.setText("Bienvenido, " + nombre);
        actualizarEstadisticas();
    }

    private void actualizarEstadisticas() {
        if (lblOcupacion != null && lblDisponibles != null) {
            double ocupacion = servicioHabitaciones.calcularPorcentajeOcupacion();
            long disponibles = servicioHabitaciones.contarHabitacionesDisponibles();

            lblOcupacion.setText(String.format("%.1f%%", ocupacion));
            lblDisponibles.setText(String.valueOf(disponibles));
        }
    }

    @FXML
    public void abrirGestionClientes(javafx.scene.input.MouseEvent event) throws IOException {
        cambiarEscena(event, "/edu/hoteluq/Vistas/vistaGestionClientes.fxml", "HotelUQ - Gestión de Clientes");
    }

    @FXML
    public void abrirGestionHabitaciones(javafx.scene.input.MouseEvent event) throws IOException {
        cambiarEscena(event, "/edu/hoteluq/Vistas/vistaGestionHabitaciones.fxml", "HotelUQ - Gestión de Habitaciones");
    }

    @FXML
    public void abrirGestionReservas(javafx.scene.input.MouseEvent event) {
        System.out.println("Abriendo gestión de reservas...");
    }

    @FXML
    public void abrirGestionServicios(javafx.scene.input.MouseEvent event) {
        System.out.println("Abriendo gestión de servicios...");
    }

    @FXML
    public void abrirGestionPromociones(javafx.scene.input.MouseEvent event) {
        System.out.println("Abriendo gestión de promociones...");
    }

    @FXML
    public void abrirGenerarReportes(javafx.scene.input.MouseEvent event) {
        System.out.println("Abriendo reportes...");
    }

    public void cerrarSesion(ActionEvent evento) throws IOException {
        cambiarEscena(evento, "/edu/hoteluq/Vistas/vistaInicio.fxml", "HotelUQ");
    }

    private void cambiarEscena(ActionEvent evento, String fxmlPath, String titulo) throws IOException {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene escena = new Scene(cargador.load());
        Stage ventana = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        ventana.setScene(escena);
        ventana.setTitle(titulo);
        ventana.show();
    }

    private void cambiarEscena(javafx.scene.input.MouseEvent evento, String fxmlPath, String titulo) throws IOException {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene escena = new Scene(cargador.load());
        Stage ventana = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        ventana.setScene(escena);
        ventana.setTitle(titulo);
        ventana.show();
    }
}
