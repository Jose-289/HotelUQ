package edu.hoteluq.controlador;

import edu.hoteluq.modelo.Cliente;
import edu.hoteluq.modelo.Usuario;
import edu.hoteluq.servicio.ServicioUsuarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControladorGestionClientes {

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, String> columnaDocumento;

    @FXML
    private TableColumn<Cliente, String> columnaNombre;

    @FXML
    private TableColumn<Cliente, String> columnaCorreo;

    @FXML
    private TableColumn<Cliente, String> columnaTelefono;

    private final ServicioUsuarios servicioUsuarios = ServicioUsuarios.getInstancia();

    @FXML
    public void initialize(){

        columnaDocumento.setCellValueFactory(new PropertyValueFactory<>("id"));

        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        cargarClientes();
    }

    private void cargarClientes(){

        ObservableList<Cliente> clientes = FXCollections.observableArrayList();

        for (Usuario usuario : servicioUsuarios.obtenerUsuarios()){

            if (usuario instanceof Cliente){
                clientes.add((Cliente) usuario);
            }
        }

        tablaClientes.setItems(clientes);
    }
}
