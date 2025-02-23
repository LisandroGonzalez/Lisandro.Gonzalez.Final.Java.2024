package controllers;

import java.io.IOException;
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
import modelos.entidades.Mochila;
import modelos.gestores.GestorGenerico;
import validadores.ValidadorProducto;

public class FXMLAnchorPaneMochilasController implements Initializable, ValidadorProducto {

    @FXML
    private Button btnMochilaAgregar;

    @FXML
    private Button btnMochilaEliminar;

    @FXML
    private Button btnMochilaModificar;
    
    @FXML
    private Button btnMochilaGuardar;

    @FXML
    private Label lblCapacidad;

    @FXML
    private Label lblDisenio;

    @FXML
    private Label lblMarca;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblPrecio;

    @FXML
    private Label lblID;

    @FXML
    private TableColumn<Mochila, Double> tableColumnMochilaCapacidad;

    @FXML
    private TableColumn<Mochila, String> tableColumnMochilaDisenio;

    @FXML
    private TableColumn<Mochila, Integer> tableColumnMochilaID;

    @FXML
    private TableColumn<Mochila, String> tableColumnMochilaMarca;

    @FXML
    private TableColumn<Mochila, String> tableColumnMochilaNombre;

    @FXML
    private TableColumn<Mochila, Double> tableColumnMochilaPrecio;

    @FXML
    private TableView<Mochila> tableViewMochilas;
    
    // ATRIBUTOS PARA LA MANIPULACION DE DATOS
    private GestorGenerico<Mochila> gestorMochilas = new GestorGenerico("mochilas.txt", Mochila.class);
    private ObservableList<Mochila> obsListMochilas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTableViewMochilas();
        
        // Se encarga de manejar las alteraciones en la TableView cuando se selecciona un elemento
        tableViewMochilas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionarItemTableViewMochilas(newValue));
    }    
    
    /**
     * Indica que valores va a recibir cada columna y carga la tabla con los lapiceras que haya
     */
    public void cargarTableViewMochilas() {
        // Indica que va a recibir cada columna de la TableView
        tableColumnMochilaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnMochilaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tableColumnMochilaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tableColumnMochilaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tableColumnMochilaDisenio.setCellValueFactory(new PropertyValueFactory<>("disenio"));
        tableColumnMochilaCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        
        // Carga la lista de lapiceras en una ObservableList para poder usarla dentro de JavaFX
        obsListMochilas = FXCollections.observableArrayList(gestorMochilas.getLista());
        
        tableViewMochilas.refresh();
        
        // Le inserta las lapiceras que haya en la lista a la TableView
        tableViewMochilas.setItems(obsListMochilas);
    }
    
    /**
     * Carga los labels del Grid de cada atributo cuando se selecciona una lapicera del TableView 
     * @param mochila
     */
    public void seleccionarItemTableViewMochilas(Mochila mochila) {
        if (mochila != null) {
            lblID.setText(String.valueOf(mochila.getId()));
            lblNombre.setText(mochila.getNombre());
            lblPrecio.setText(String.valueOf(mochila.getPrecio()));
            lblMarca.setText(mochila.getMarca());
            lblDisenio.setText(mochila.getDisenio());
            lblCapacidad.setText(String.valueOf(mochila.getCapacidad()));
        }
        else {
            lblID.setText("");
            lblNombre.setText("");
            lblPrecio.setText("");
            lblMarca.setText("");
            lblDisenio.setText("");
            lblCapacidad.setText("");
        }
    }
    
    @FXML
    public void handleAgregarMochila() throws IOException {
        // Se crea una mochila vacia para luego settearle los datos el la ventana de dialogo
        Mochila mochila = new Mochila();
        mochila.setId(gestorMochilas.obtenerNuevoID(gestorMochilas.getLista()));
        boolean confirmado = abrirFXMLAnchorPaneMochilasDialogo(mochila);
        
        if(confirmado) {
            gestorMochilas.agregar(mochila);
            cargarTableViewMochilas();
        }
    }
    
    @FXML
    public void handleModificarMochila() throws IOException {
        // Se crea un obj Lapicera con el item seleccionado en el TableView
        Mochila mochila = tableViewMochilas.getSelectionModel().getSelectedItem();
        
        if(mochila != null) {
            boolean confirmado = abrirFXMLAnchorPaneMochilasDialogo(mochila);
            if (confirmado) {
                gestorMochilas.modificar(mochila);
                cargarTableViewMochilas();
            }
        }
        else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Por favor, indique la mochila a modificar.");
            alerta.show();
        }
    }
    
    @FXML
    public void handleEliminarMochila() {
        // Se crea un obj Lapicera con el item seleccionado en el TableView
        Mochila mochila = tableViewMochilas.getSelectionModel().getSelectedItem();
        
        if(mochila != null) {
            gestorMochilas.eliminar(mochila);
            cargarTableViewMochilas();
        }
        else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Por favor, indique la mochila a eliminar.");
            alerta.show();
        }
    }
    
    @FXML
    public void handleGuardarMochila() {
        gestorMochilas.guardarCambios();
    }
    
    public boolean abrirFXMLAnchorPaneMochilasDialogo(Mochila mochila) throws IOException {
        // Le asigna al loader la direccion de la vista del dialogo
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneMochilasDialogoController.class.getResource("/vista/FXMLAnchorPaneMochilasDialogo.fxml"));
        AnchorPane ap = (AnchorPane) loader.load();
        
        // Se crea la ventana para el dialogo
        Stage ventanaDialogo = new Stage();
        ventanaDialogo.setTitle("Datos de la mochila");
        Scene escena = new Scene(ap);
        ventanaDialogo.setScene(escena);
        
        // Settea el cliente y la ventana
        FXMLAnchorPaneMochilasDialogoController controller = loader.getController();
        controller.setVentana(ventanaDialogo);
        controller.setMochila(mochila);
        
        // Espera a que el usuario tome una decision
        ventanaDialogo.showAndWait();
        
        // Retorna si se confirmo o se cancelo
        return controller.isBtnConfirmarApretado();
    }
}
