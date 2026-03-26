package dam.code;

import dam.code.persistency.UsuariosDAO;
import dam.code.service.PersonaService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase GestionPeliculasApp que arranca la aplicación de Gestión de Películas.
 *
 * @author Alumno - Marco Martin
 * @version 1.0
 */
public class GestionPeliculasApp extends Application {

    /**
     * Inicializa y muestra la ventana principal de la aplicación.
     * Carga por defecto la vista de registro de usuarios.
     *
     * @param stage el escenario principal de JavaFX
     * @throws Exception si hay algún error al cargar el archivo FXML
     */
    @Override
    public void start(Stage stage) throws Exception {
        PersonaService personaService = new PersonaService(new UsuariosDAO());

        if(personaService.getRegistros().isEmpty()||personaService.getRegistros()==null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/personas_view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 600, 650);
            stage.setScene(scene);
            stage.setTitle("Peliculas");

            stage.show();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inicio_view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 650);
        stage.setScene(scene);
        stage.setTitle("Peliculas");

        stage.show();
    }

    /**
     * Metodo de entrada estándar para lanzar la aplicación JavaFX.
     *
     * @param args argumentos
     */
    public static void main(String[] args) {
        launch(args);
    }
}