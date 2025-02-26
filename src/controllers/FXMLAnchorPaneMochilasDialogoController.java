package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.entidades.Mochila;
import servicios.ValidadorProducto;

public class FXMLAnchorPaneMochilasDialogoController implements Initializable, ValidadorProducto {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txtFieldCapacidad;

    @FXML
    private TextField txtFieldDisenio;

    @FXML
    private TextField txtFieldMarca;

    @FXML
    private TextField txtFieldNombre;

    @FXML
    private TextField txtFieldPrecio;
    
    // Ventana de dialogo
    private Stage ventana;
    
    // Atributos para la manipulacion de datos
    private boolean btnConfirmarApretado = false;
    private Mochila mochila;

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

    public Mochila getMochila() {
        return mochila;
    }

    public void setMochila(Mochila mochila) {
        this.mochila = mochila;
        txtFieldNombre.setText(mochila.getNombre());
        txtFieldPrecio.setText(String.valueOf(mochila.getPrecio()));
        txtFieldMarca.setText(mochila.getMarca());
        txtFieldDisenio.setText(mochila.getDisenio());
        txtFieldCapacidad.setText(String.valueOf(mochila.getCapacidad()));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    public void handleBtnConfirmar() {
        if (datosValidos()) {
            mochila.setNombre(txtFieldNombre.getText());
            mochila.setPrecio(Double.parseDouble(txtFieldPrecio.getText()));
            mochila.setMarca(txtFieldMarca.getText());
            mochila.setDisenio(txtFieldDisenio.getText());
            mochila.setCapacidad(Double.parseDouble(txtFieldCapacidad.getText()));
            btnConfirmarApretado = true;
            ventana.close();
        }
    }
    
    @FXML
    public void handleBtnCancelar() {
        ventana.close(); // Cierra la ventana de dialogos sin modificar la lista
    }
    
    private boolean datosValidos() {
        String mensajeError = "";
        
        if(!isStringValido(txtFieldNombre.getText()))   { mensajeError += "Nombre invalido.\n"; }
        if(!isNumeroPositivo(txtFieldPrecio.getText()))   { mensajeError += "Precio invalido.\n"; }
        if(!isStringValido(txtFieldMarca.getText()))    { mensajeError += "Marca invalida.\n"; }
        if(!isStringValido(txtFieldDisenio.getText()))  { mensajeError += "Diseño invalido.\n"; }
        if(!isNumeroPositivo(txtFieldCapacidad.getText()) && Double.parseDouble(txtFieldCapacidad.getText()) < 20) { 
            mensajeError += "Capacidad invalida.\n";
        }

        // Si la cadena no esta vacia, entonces hubo un error en el ingreso de datos
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
