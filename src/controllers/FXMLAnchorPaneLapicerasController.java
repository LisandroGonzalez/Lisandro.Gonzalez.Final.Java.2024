package controllers;

import java.io.IOException;
import modelos.entidades.Color;
import modelos.entidades.Lapicera;
import modelos.entidades.TipoLapicera;
import modelos.gestores.GestorGenerico;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import servicios.ValidadorProducto;

public class FXMLAnchorPaneLapicerasController implements Initializable, ValidadorProducto {

    @FXML
    private Button btnLapiceraAgregar;

    @FXML
    private Button btnLapiceraEliminar;

    @FXML
    private Button btnLapiceraModificar;
    
    @FXML
    private Button btnLapiceraGuardar;

    @FXML
    private Label lblColor;

    @FXML
    private Label lblID;

    @FXML
    private Label lblMarca;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblPrecio;

    @FXML
    private Label lblTipo;


    @FXML
    private TableColumn<Lapicera, Color> tableColumnLapiceraColor;

    @FXML
    private TableColumn<Lapicera, Integer> tableColumnLapiceraID;

    @FXML
    private TableColumn<Lapicera, String> tableColumnLapiceraMarca;

    @FXML
    private TableColumn<Lapicera, String> tableColumnLapiceraNombre;

    @FXML
    private TableColumn<Lapicera, Double> tableColumnLapiceraPrecio;

    @FXML
    private TableColumn<Lapicera, TipoLapicera> tableColumnLapiceraTipo;
    
    @FXML
    private TableView<Lapicera> tableViewLapiceras;

    
    // ATRIBUTOS PARA LA MANIPULACION DE DATOS
    private GestorGenerico<Lapicera> gestorLapiceras = new GestorGenerico("lapiceras.txt", Lapicera.class);
    private ObservableList<Lapicera> obsListLapiceras;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTableViewLapiceras();
        
        // Se encarga de manejar las alteraciones en la TableView cuando se selecciona un elemento
        tableViewLapiceras.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionarItemTableViewLapiceras(newValue));
    }    
    
    /**
     * Indica que valores va a recibir cada columna y carga la tabla con los lapiceras que haya
     */
    public void cargarTableViewLapiceras() {
        // Indica que va a recibir cada columna de la TableView
        tableColumnLapiceraID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnLapiceraNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tableColumnLapiceraPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tableColumnLapiceraMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tableColumnLapiceraColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        tableColumnLapiceraTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        
        // Carga la lista de lapiceras en una ObservableList para poder usarla dentro de JavaFX
        obsListLapiceras = FXCollections.observableArrayList(gestorLapiceras.getLista());
        
        // Necesario para la actualizacion
        tableViewLapiceras.refresh();
        
        // Le inserta las lapiceras que haya en la lista a la TableView
        tableViewLapiceras.setItems(obsListLapiceras);
    }
    
    /**
     * Carga los labels del Grid de cada atributo cuando se selecciona una lapicera del TableView
     * @param lapicera 
     */
    public void seleccionarItemTableViewLapiceras(Lapicera lapicera) {
        if (lapicera != null) {
            lblID.setText(String.valueOf(lapicera.getId()));
            lblNombre.setText(lapicera.getNombre());
            lblPrecio.setText(String.valueOf(lapicera.getPrecio()));
            lblMarca.setText(lapicera.getMarca());
            lblColor.setText(lapicera.getColor().toString());
            lblTipo.setText(lapicera.getTipo().toString());
        }
        else {
            lblID.setText("");
            lblNombre.setText("");
            lblPrecio.setText("");
            lblMarca.setText("");
            lblColor.setText("");
            lblTipo.setText("");
        }
    }
    
    @FXML
    public void handleAgregarLapicera() throws IOException {
        // Se crea una lapicera vacia para luego settearle los datos el la ventana de dialogo
        Lapicera lapicera = new Lapicera();
        
        // Se le asigna el id
        lapicera.setId(gestorLapiceras.obtenerNuevoID(gestorLapiceras.getLista()));
        
        // Abre la ventana para ingresar los datos de la Lapicera y guarda el retorno
        boolean confirmado = abrirFXMLAnchorPaneLapicerasDialogo(lapicera);
        
        // En caso de que se haya confirmado la agregacion
        if(confirmado) {
            gestorLapiceras.agregar(lapicera);
            cargarTableViewLapiceras();
        }
    }
    
    @FXML
    public void handleModificarLapicera() throws IOException {
        // Se crea un obj Lapicera con los valores del item seleccionado en el TableView
        Lapicera lapicera = tableViewLapiceras.getSelectionModel().getSelectedItem();
        
        if(lapicera != null) {
            // Abre la ventana para modificar los datos de la Lapicera y guarda el retorno
            boolean confirmado = abrirFXMLAnchorPaneLapicerasDialogo(lapicera);
            
            // En caso de que se haya confirmado la modificacion
            if (confirmado) {
                gestorLapiceras.modificar(lapicera);
                cargarTableViewLapiceras();
            }
        }
        else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Por favor, indique la lapicera a modificar.");
            alerta.show();
        }
    }
    
    @FXML
    public void handleEliminarLapicera() {
        // Se crea un obj Lapicera con el item seleccionado en el TableView
        Lapicera lapicera = tableViewLapiceras.getSelectionModel().getSelectedItem();
        
        if(lapicera != null) {
            gestorLapiceras.eliminar(lapicera);
            cargarTableViewLapiceras();
        }
        else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Por favor, indique la lapicera a eliminar.");
            alerta.show();
        }
    }
    
    @FXML
    public void handleGuardarLapicera() {
        gestorLapiceras.guardarCambios();
    }
    
    public boolean abrirFXMLAnchorPaneLapicerasDialogo(Lapicera lapicera) throws IOException {
        // Le asigna al loader la direccion de la vista del dialogo
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneLapicerasDialogoController.class
                .getResource("/vista/FXMLAnchorPaneLapicerasDialogo.fxml"));
        AnchorPane ap = (AnchorPane) loader.load();
        
        // Se crea la ventana para el dialogo
        Stage ventanaDialogo = new Stage();
        ventanaDialogo.setTitle("Datos de la lapicera");
        Scene escena = new Scene(ap);
        ventanaDialogo.setScene(escena);
        
        // Settea el cliente y la ventana
        FXMLAnchorPaneLapicerasDialogoController controller = loader.getController();
        controller.setVentana(ventanaDialogo);
        controller.setLapicera(lapicera);
        
        // Espera a que el usuario tome una decision
        ventanaDialogo.showAndWait();
        
        // Retorna si se confirmo o se cancelo
        return controller.isBtnConfirmarApretado();
    }
}
