package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.entidades.GeneroLibro;
import static modelos.entidades.GeneroLibro.HISTORIA;
import modelos.entidades.Libro;
import validadores.ValidadorProducto;
import servicios.Auxiliar;

public class FXMLAnchorPaneLibrosDialogoController implements Initializable, ValidadorProducto, Auxiliar<Libro> {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private ChoiceBox<String> choiceBoxGenero;

    @FXML
    private TextField txtFieldAutor;

    @FXML
    private TextField txtFieldMarca;

    @FXML
    private TextField txtFieldNombre;

    @FXML
    private TextField txtFieldPrecio;
    
    @FXML
    private TextField txtFieldCantHojas;
    
    private Stage ventana;
    private boolean btnConfirmarApretado = false;
    private Libro libro;

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }

    public boolean isBtnConfirmarApretado() {
        return btnConfirmarApretado;
    }

    public void setBtnConfirmarApretado(boolean btnConfirmarApretado) {
        this.btnConfirmarApretado = btnConfirmarApretado;
    }
    
    public Libro getLibro() {
        return libro;
    }
    
    public void setLibro(Libro libro) {
        this.libro = libro;
        txtFieldNombre.setText(libro.getNombre());
        txtFieldPrecio.setText(String.valueOf(libro.getPrecio()));
        txtFieldMarca.setText(libro.getMarca());
        txtFieldCantHojas.setText(String.valueOf(libro.getCantHojas()));
        txtFieldAutor.setText(libro.getAutor());
        
        // De haber entrado por el boton modificar, se le asignan valores por defecto
        if (libro.getGenero() == null) {
            choiceBoxGenero.setValue(String.valueOf(HISTORIA));
        } else { 
            // De haber entrado por agregar se le asignan los valores de la lapicera
            choiceBoxGenero.setValue(String.valueOf(libro.getGenero()));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarMenuButtons();
    }    
    
    public void handleBtnConfirmar() {
        if (datosValidos()) {
            libro.setNombre(txtFieldNombre.getText());
            libro.setPrecio(Double.parseDouble(txtFieldPrecio.getText()));
            libro.setMarca(txtFieldMarca.getText());
            libro.setCantHojas(Integer.valueOf(txtFieldCantHojas.getText()));
            libro.setAutor(txtFieldAutor.getText());
            libro.setGenero(GeneroLibro.valueOf(choiceBoxGenero.getValue()));
            btnConfirmarApretado = true;
            ventana.close();
        }
    }
    
    public void handleBtnCancelar() {
        ventana.close();
    }
    
    private void cargarMenuButtons() {
        // Obtengo las ObservableList mediante el llamado a obtenerValoresEnum(), el cual retorna una lista de Strings
        // con los valores del Enum pasado por parametro, a la que luego se la transforma en obs list para poder
        // desplegarla en los menu buttons.
        ObservableList generosObsList = FXCollections.observableArrayList(obtenerValoresEnum(GeneroLibro.class));
        
        // Se le cargan los generos
        choiceBoxGenero.setItems(generosObsList);
    }
    
    private boolean datosValidos() {
        String mensajeError = "";
        
        if(!isStringValido(txtFieldNombre.getText())) {
            mensajeError += "Nombre invalido.\n";
        }
        if(!isNumeroPositivo(txtFieldPrecio.getText())) {
            mensajeError += "Precio invalido.\n";
        }
        if(!isStringValido(txtFieldMarca.getText())) {
            mensajeError += "Marca invalida.\n";
        }
        if(!isNumeroPositivo(txtFieldCantHojas.getText())) {
            mensajeError += "Cantidad de hojas invalida.\n";
        }
        if(!isStringValido(txtFieldAutor.getText())) {
            mensajeError += "Autor invalido.\n";
        }
        if(choiceBoxGenero.getSelectionModel().isEmpty()) {
            mensajeError += "Genero invalido.\n";
        }

        if(isStringValido(mensajeError)) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error.");
            alerta.setContentText(mensajeError);
            alerta.show();
            return false;
        }
        else {
            return true;
        } 
    }
}
