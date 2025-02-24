package controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLVBoxMainController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MenuItem menuItemCuadernos;

    @FXML
    private MenuItem menuItemGrafico;

    @FXML
    private MenuItem menuItemLapiceras;

    @FXML
    private MenuItem menuItemLibros;

    @FXML
    private MenuItem menuItemMochilas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    /**
     * Carga el anchor pane de lapiceras al clickear sobre el menu item.
     * @throws IOException 
     */
    @FXML
    public void handleMenuItemLapiceras() throws IOException {
        // Obtiene la direccion del AnchorPane del CRUD de lapiceras
        AnchorPane ap = (AnchorPane) FXMLLoader.load(getClass().getResource("/vista/FXMLAnchorPaneLapiceras.fxml"));
        
        // Carga dentro del AnchorPane del Main el de Lapiceras
        anchorPane.getChildren().setAll(ap);
    }
    
    /**
     * Carga el anchor pane de mochilas al clickear sobre el menu item.
     * @throws IOException 
     */
    @FXML
    public void handleMenuItemMochilas() throws IOException {
        // Obtiene la direccion del AnchorPane del CRUD de mochilas
        AnchorPane ap = (AnchorPane) FXMLLoader.load(getClass().getResource("/vista/FXMLAnchorPaneMochilas.fxml"));
        
        // Carga dentro del AnchorPane del Main el de Mochilas
        anchorPane.getChildren().setAll(ap);
    }
    
    /**
     * Carga el anchor pane de libros al clickear sobre el menu item.
     * @throws IOException 
     */
    @FXML
    public void handleMenuItemLibros() throws IOException {
        // Obtiene la direccion del AnchorPane del CRUD de libros
        AnchorPane ap = (AnchorPane) FXMLLoader.load(getClass().getResource("/vista/FXMLAnchorPaneLibros.fxml"));
        
        // Carga dentro del AnchorPane del Main el de Libros
        anchorPane.getChildren().setAll(ap);
    }
    
    @FXML
    public void handleMenuItemTextoProductos() throws IOException {
        // Le asigna al loader la direccion de la vista del dialogo
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneListadoProductosController.class.getResource("/vista/FXMLAnchorPaneListadoProductos.fxml"));
        AnchorPane ap = (AnchorPane) loader.load();
        
        // Se crea la ventana para el dialogo
        Stage ventana = new Stage();
        ventana.setTitle("Productos en formato txt");
        Scene escena = new Scene(ap);
        ventana.setScene(escena);
        
        // Settea el cliente y la ventana
        FXMLAnchorPaneListadoProductosController controller = loader.getController();
        controller.setVentana(ventana);
        ventana.show();
    }
}
