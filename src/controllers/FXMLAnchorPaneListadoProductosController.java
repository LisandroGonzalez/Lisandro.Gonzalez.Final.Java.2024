package controllers;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.entidades.Lapicera;
import modelos.entidades.Libro;
import modelos.entidades.Mochila;
import modelos.entidades.Producto;
import modelos.gestores.GestorGenerico;
import static modelos.repositorios.RepositorioGenerico.importarATxt;
import servicios.IdentificableFunc;
import servicios.ValidadorProducto;
import servicios.ListFunc;

public class FXMLAnchorPaneListadoProductosController<T extends Producto> 
        implements Initializable, ValidadorProducto, ListFunc, IdentificableFunc<T>{

    @FXML
    private Button btnAplicarFiltro;
    
    @FXML
    private Button btnImportarTxt;
    
    @FXML
    private Button btnOdernarPorID;

    @FXML
    private Button btnOdernarPorMarca;

    @FXML
    private Button btnOdernarPorNombre;

    @FXML
    private Button btnOdernarPorPrecio;

    @FXML
    private ChoiceBox<String> choiceBoxTipoProducto;
    
    @FXML
    private TextField txtFieldNombreArchTxt;
    
    @FXML
    private TextField txtFieldPrecioMinimo;
    
    @FXML
    private TextField txtFieldPrecioMaximo;

    @FXML
    private TextArea txtAreaProductos;
    
    private Stage ventana;
    private List<String> tiposProducto = List.of("Lapicera", "Mochila", "Libro");
    private GestorGenerico<T> gestorProductos;
    private List<T> listado; 
    
    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtAreaProductos.setEditable(false);
        cargarMenuItemProductos();
        choiceBoxTipoProducto.setOnAction(this::listarProductos);
    }
    
    public void listarProductos(ActionEvent event) {
        if (choiceBoxTipoProducto.getValue() != null) {
            switch (choiceBoxTipoProducto.getValue()) {
                case "Lapicera":
                    gestorProductos = new GestorGenerico("lapiceras.txt", Lapicera.class);
                    break;
                case "Mochila":
                    gestorProductos = new GestorGenerico("mochilas.txt", Mochila.class);
                    break;
                case "Libro":
                    gestorProductos = new GestorGenerico("libros.txt", Libro.class);
                    break;
            }
            listado = gestorProductos.getLista();
            
            try {
            txtAreaProductos.setText(obtenerListaEnString(listado, 
                    listado.getFirst().getClass().getSimpleName()+"s"));
            } catch(NoSuchElementException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    @FXML
    public void handleBtnAplicarFiltro(){
        if (choiceBoxTipoProducto.getValue() != null) {
            /* Se le asigna null para que no tire error de compilacion, pero el flag ya se encarga de
            corroborar que no se aplique el filtro a menos que se hayan completado correctamente
            los campos */
            Predicate<T> filtro = null;
            
            // Bandera que determina si se applica el filtro
            boolean flag;
            
            // CASO 1: Ambos son numeros enteros
            if(isNumeroPositivo(txtFieldPrecioMinimo.getText()) && isNumeroPositivo(txtFieldPrecioMaximo.getText())) {
                
                // Confirma que esten bien ingresados los numeros
                if (Double.parseDouble(txtFieldPrecioMinimo.getText()) < Double.parseDouble(txtFieldPrecioMaximo.getText())) {
                    filtro = t -> t.getPrecio() >= Double.parseDouble(txtFieldPrecioMinimo.getText()) 
                            && t.getPrecio() <= Double.parseDouble(txtFieldPrecioMaximo.getText());
                    flag = true;
                }
                else {
                    flag = false;
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error.");
                    alerta.setContentText("Los numeros ingresados no son validos.");
                    alerta.show();
                }
            }
            // CASO 2: El minimo es un numero entero y el maximo esta vacio
            else if (isNumeroPositivo(txtFieldPrecioMinimo.getText()) && !isStringValido(txtFieldPrecioMaximo.getText())) {
                filtro = t -> t.getPrecio() >= Double.parseDouble(txtFieldPrecioMinimo.getText());
                flag = true;
            }
            // CASO 3: El minimo esta vacio y el maximo es un numero entero
            else if (!isStringValido(txtFieldPrecioMinimo.getText()) && isNumeroPositivo(txtFieldPrecioMaximo.getText())) {
                filtro = t -> t.getPrecio() <= Double.parseDouble(txtFieldPrecioMaximo.getText());
                flag = true;
            }
            // CASO 4: Ambos campos estan vacios
            else if (!isStringValido(txtFieldPrecioMinimo.getText()) && !isStringValido(txtFieldPrecioMaximo.getText())) {
                flag = false;
             }
            else { // Envia una alerta en caso de que no sean numeros validos
                flag = false;
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error.");
                alerta.setContentText("Utiliza un formato que coincida con el solicitado.");
                alerta.show();
            }
            
            // En caso de que se haya definido un filtro
            if(flag) {
                listado = filtrarLista(gestorProductos.getLista(), filtro);
                txtAreaProductos.setText(obtenerListaEnString(listado));
            } 
            // En caso de que los campos Min y Max esten vacios o se hayan ingresado datos invalidos
            else {
                listado = gestorProductos.getLista();
                txtAreaProductos.setText(obtenerListaEnString(listado, listado.getFirst().getClass().getSimpleName()+"s"));
            }
        }
    }
    
    private void cargarMenuItemProductos() {
        ObservableList generosObsList = FXCollections.observableArrayList(tiposProducto);
        choiceBoxTipoProducto.setItems(generosObsList);
    }
    
    @FXML
    public void handleBtnOdernarPorID() {
        if (choiceBoxTipoProducto.getValue() != null) {
            Comparator<T> comp = (p1, p2) -> p1.compareTo(p2);
            ordenarListado(comp);
        }
    }
    
    @FXML
    public void handleBtnOdernarPorNombre() {
        if (choiceBoxTipoProducto.getValue() != null) {
            Comparator<T> comp = (p1, p2) -> T.compareByNombre(p1, p2);
            ordenarListado(comp);
        }
    }
    
    @FXML
    public void handleBtnOdernarPorPrecio() {
        if (choiceBoxTipoProducto.getValue() != null) {
            Comparator<T> comp = (p1, p2) -> T.compareByPrecio(p1, p2);
            ordenarListado(comp);
        }
    }
    
    @FXML
    public void handleBtnOdernarPorMarca() {
        if (choiceBoxTipoProducto.getValue() != null) {
            Comparator<T> comp = (p1, p2) -> T.compareByMarca(p1, p2);
            ordenarListado(comp);
        }
    }
    
    private void ordenarListado(Comparator<T> comparacion) {
        listado = ordenarLista(listado, comparacion);
        txtAreaProductos.setText(obtenerListaEnString(listado));
    }
    
    @FXML
    public void handleBtnImportarTxt() {
        // Si el nombre asignado al archivo txt es valido
        if (isStringValido(txtFieldNombreArchTxt.getText()) && txtFieldNombreArchTxt.getText().endsWith(".txt")) {
            // Obtiene el listado entero en una cadena
            String listadoStr = obtenerListaEnString(listado, txtFieldNombreArchTxt.getText().replace(".txt", ""));
            
            // Lo guarda
            importarATxt(txtFieldNombreArchTxt.getText(), listadoStr);
        }
        else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error.");
            alerta.setContentText("Utiliza un formato que coincida con el solicitado (.txt).");
            alerta.show();
        }
    }
}
