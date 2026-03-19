package dam.code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestionPeliculasApp extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/personas_view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 650);
        stage.setScene(scene);
        stage.setTitle("Peliculas");

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
