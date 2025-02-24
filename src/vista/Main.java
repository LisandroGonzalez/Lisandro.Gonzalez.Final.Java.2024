package vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLVBoxMain.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Libreria");
        stage.setResizable(false);
        stage.show();
    }
}
