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
import modelos.entidades.GeneroLibro;
import modelos.entidades.Libro;
import modelos.gestores.GestorGenerico;
import validadores.ValidadorProducto;

public class FXMLAnchorPaneLibrosController implements Initializable, ValidadorProducto {

    @FXML
    private Button btnLibroAgregar;

    @FXML
    private Button btnLibroEliminar;

    @FXML
    private Button btnLibroModificar;
    
    @FXML
    private Button btnLibroGuardar;

    @FXML
    private Label lblAutor;

    @FXML
    private Label lblGenero;

    @FXML
    private Label lblID;

    @FXML
    private Label lblMarca;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblPrecio;
    
    @FXML
    private Label lblCantHojas;

    @FXML
    private TableColumn<Libro, String> tableColumnLibroAutor;

    @FXML
    private TableColumn<Libro, GeneroLibro> tableColumnLibroGenero;

    @FXML
    private TableColumn<Libro, Integer> tableColumnLibroID;

    @FXML
    private TableColumn<Libro, String> tableColumnLibroMarca;

    @FXML
    private TableColumn<Libro, String> tableColumnLibroNombre;

    @FXML
    private TableColumn<Libro, Double> tableColumnLibroPrecio;
    
    @FXML
    private TableColumn<Libro, Integer> tableColumnLibroCantHojas;

    @FXML
    private TableView<Libro> tableViewLibros;
    
    // ATRIBUTOS PARA LA MANIPULACION DE DATOS
    private GestorGenerico<Libro> gestorLibros = new GestorGenerico("libros.txt", Libro.class);
    private ObservableList<Libro> obsListLibros;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTableViewLibros();
        
        // Se encarga de manejar las alteraciones en la TableView cuando se selecciona un elemento
        tableViewLibros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionarItemTableViewLibros(newValue));
    }
    
    /**
     * Indica que valores va a recibir cada columna y carga la tabla con los libros que haya
     */
    public void cargarTableViewLibros() {
        // Indica que va a recibir cada columna de la TableView
        tableColumnLibroID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnLibroNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tableColumnLibroPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tableColumnLibroMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tableColumnLibroCantHojas.setCellValueFactory(new PropertyValueFactory<>("cantHojas"));
        tableColumnLibroAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tableColumnLibroGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        
        // Carga la lista de libros en una ObservableList para poder usarla dentro de JavaFX
        obsListLibros = FXCollections.observableArrayList(gestorLibros.getLista());
        
        tableViewLibros.refresh();
        
        // Le inserta los libros que haya en la lista a la TableView
        tableViewLibros.setItems(obsListLibros);
    }
    
    /**
     * Carga los labels del Grid de cada atributo cuando se selecciona un libro del TableView
     * @param libro 
     */
    public void seleccionarItemTableViewLibros(Libro libro) {
        if (libro != null) {
            lblID.setText(String.valueOf(libro.getId()));
            lblNombre.setText(libro.getNombre());
            lblPrecio.setText(String.valueOf(libro.getPrecio()));
            lblMarca.setText(libro.getMarca());
            lblCantHojas.setText(String.valueOf(libro.getCantHojas()));
            lblAutor.setText(libro.getAutor());
            lblGenero.setText(libro.getGenero().toString());
        }
        else {
            lblID.setText("");
            lblNombre.setText("");
            lblPrecio.setText("");
            lblMarca.setText("");
            lblCantHojas.setText("");
            lblAutor.setText("");
            lblGenero.setText("");
        }
    }
    
    @FXML
    void handleAgregarLibro() throws IOException {
        // Se crea un libro vacio para luego settearle los datos en la ventana de dialogo
        Libro libro = new Libro();
        libro.setId(gestorLibros.obtenerNuevoID(gestorLibros.getLista()));
        
        boolean confirmado = abrirFXMLAnchorPaneLibrosDialogo(libro);
        
        if(confirmado) {
            gestorLibros.agregar(libro);
            cargarTableViewLibros();
        }
    }

    @FXML
    void handleModificarLibro() throws IOException {
        // Se crea un obj Libro con el item seleccionado en el TableView
        Libro libro = tableViewLibros.getSelectionModel().getSelectedItem();
        
        if(libro != null) {
            boolean confirmado = abrirFXMLAnchorPaneLibrosDialogo(libro);
            if (confirmado) {
                gestorLibros.modificar(libro);
                cargarTableViewLibros();
            }
        }
        else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Por favor, indique el libro a modificar.");
            alerta.show();
        }
    }

    @FXML
    void handleEliminarLibro() {
        // Se crea un obj Libro con el item seleccionado en el TableView
        Libro libro = tableViewLibros.getSelectionModel().getSelectedItem();
        
        if(libro != null) {
            gestorLibros.eliminar(libro);
            cargarTableViewLibros();
        }
        else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Por favor, indique la lapicera a eliminar.");
            alerta.show();
        }
    }
    
    @FXML
    public void handleGuardarLibro() {
        gestorLibros.guardarCambios();
    }
    
    public boolean abrirFXMLAnchorPaneLibrosDialogo(Libro libro) throws IOException {
        // Le asigna al loader la direccion de la vista del dialogo
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneLibrosDialogoController.class.getResource("/vista/FXMLAnchorPaneLibrosDialogo.fxml"));
        AnchorPane ap = (AnchorPane) loader.load();
        
        // Se crea la ventana para el dialogo
        Stage ventanaDialogo = new Stage();
        ventanaDialogo.setTitle("Datos del libro");
        Scene escena = new Scene(ap);
        ventanaDialogo.setScene(escena);
        
        // Settea el cliente y la ventana
        FXMLAnchorPaneLibrosDialogoController controller = loader.getController();
        controller.setVentana(ventanaDialogo);
        controller.setLibro(libro);
        
        // Espera a que el usuario tome una decision
        ventanaDialogo.showAndWait();
        
        // Retorna si se confirmo o se cancelo
        return controller.isBtnConfirmarApretado();
    }
}
