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
import modelos.entidades.Color;
import static modelos.entidades.Color.NEGRO;
import modelos.entidades.Lapicera;
import modelos.entidades.TipoLapicera;
import static modelos.entidades.TipoLapicera.CON_TAPA;
import validadores.ValidadorProducto;
import servicios.Auxiliar;

public class FXMLAnchorPaneLapicerasDialogoController implements Initializable, ValidadorProducto, Auxiliar<Lapicera> {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private ChoiceBox<String> choiceBoxColor;

    @FXML
    private ChoiceBox<String> choiceBoxTipo;

    @FXML
    private TextField txtFieldMarca;

    @FXML
    private TextField txtFieldNombre;

    @FXML
    private TextField txtFieldPrecio;
    
    private Stage ventana;
    private boolean btnConfirmarApretado = false;
    private Lapicera lapicera;

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

    public Lapicera getLapicera() {
        return lapicera;
    }

    public void setLapicera(Lapicera lapicera) {
        this.lapicera = lapicera;
        txtFieldNombre.setText(lapicera.getNombre());
        txtFieldPrecio.setText(String.valueOf(lapicera.getPrecio()));
        txtFieldMarca.setText(lapicera.getMarca());
        
        // De haber entrado por el boton modificar, se le asignan valores por defecto
        if (lapicera.getColor() == null || lapicera.getTipo() == null) {
            choiceBoxColor.setValue(String.valueOf(NEGRO));
            choiceBoxTipo.setValue(String.valueOf(CON_TAPA));
        } else { 
            // De haber entrado por agregar se le asignan los valores de la lapicera
            choiceBoxColor.setValue(String.valueOf(lapicera.getColor()));
            choiceBoxTipo.setValue(String.valueOf(lapicera.getTipo()));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarMenuButtons();
    }    
    
    public void handleBtnConfirmar() {
        if (datosValidos()) {
            lapicera.setNombre(txtFieldNombre.getText());
            lapicera.setPrecio(Double.parseDouble(txtFieldPrecio.getText()));
            lapicera.setMarca(txtFieldMarca.getText());
            lapicera.setColor(Color.valueOf(choiceBoxColor.getValue()));
            lapicera.setTipo(TipoLapicera.valueOf(choiceBoxTipo.getValue()));
            btnConfirmarApretado = true;
            ventana.close();
        }
    }
    
    public void handleBtnCancelar() {
        ventana.close();
    }
    
    private void cargarMenuButtons() {
        // Obtengo 2 ObservableList mediante el llamado a obtenerValoresEnum(), el cual retorna una lista de Strings
        // con los valores del Enum pasado por parametro, a la que luego se la transforma en obs list para poder
        // desplegarla en los menu buttons.
        ObservableList coloresObsList = FXCollections.observableArrayList(obtenerValoresEnum(Color.class));
        ObservableList tiposObsList = FXCollections.observableArrayList(obtenerValoresEnum(TipoLapicera.class));
        
        // Se le cargan los colores
        choiceBoxColor.setItems(coloresObsList);
        choiceBoxTipo.setItems(tiposObsList);
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
        if(choiceBoxColor.getSelectionModel().isEmpty()) {
            mensajeError += "Color invalido.\n";
        }
        if(choiceBoxTipo.getSelectionModel().isEmpty()) {
            mensajeError += "Tipo invalido.\n";
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
