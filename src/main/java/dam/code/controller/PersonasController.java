package dam.code.controller;

import dam.code.exceptions.PersonaExcepcion;
import dam.code.models.Persona;
import dam.code.persistency.JsonManager;
import dam.code.persistency.UsuariosDAO;
import dam.code.repository.PeliculaRepository;
import dam.code.service.PeliculaService;
import dam.code.service.PersonaService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador para la vista de registro de usuarios.
 *
 * @author Alumno- Marco Martin
 * @version 1.0
 */
public class PersonasController {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtRepetir;
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnRegistro;
    @FXML
    private Label lblMensaje;

    private final PersonaService personaService = new PersonaService(new UsuariosDAO());

    /**
     * Metodo que establece un mensaje de bienvenida
     *
     * @param mensajeBienvenida el texto a mostrar
     */
    public void setMensajeBienvenida(String mensajeBienvenida) {
        lblMensaje.setText(mensajeBienvenida);
    }

    /**
     * Metodo que valida los campos del formulario y registra al nuevo usuario
     */
    @FXML
    public void registrarUsuario() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String dni = txtDni.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String repetir = txtRepetir.getText();

        if (nombre.isEmpty() || email.isEmpty() || dni.isEmpty() || apellido.isEmpty()||password.isEmpty()||repetir.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios");
            lblMensaje.setStyle("-fx-text-fill: red");
            return;
        }

        if(!password.equals(repetir)){
            mostrarAlertErrorValidacion("Contraseñas no coincidentes");
            return;
        }

        try {
            Persona persona=new Persona(dni,nombre,apellido,email);
            personaService.addUsuario(persona,password);

            lblMensaje.setText("Usuario registrado correctamente");
            lblMensaje.setStyle("-fx-text-fill: lightgreen");
            mostrarAlertConfirm();

        } catch (PersonaExcepcion e) {
            mostrarAlertErrorValidacion(e.getMessage());
        }
    }

    /**
     * Metodo que cambia hacia la ventana de Inicio de Sesión
     */
    @FXML
    public void cambiarVentana() {
        PersonaService personaService = new PersonaService(new UsuariosDAO());
        if (personaService.getRegistros() == null || personaService.getRegistros().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso del Sistema");
            alert.setHeaderText("No hay usuarios registrados");
            alert.setContentText("Debes registrar primero el usuario");
            alert.showAndWait();

            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/inicio_view.fxml"));

            Parent root = fxmlLoader.load();

            Stage stage = (Stage) btnInicio.getScene().getWindow();

            Scene scene = new Scene(root, 600, 650);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertErrorValidacion("Error al cargar la pantalla de inicio de sesión.");
        }
    }

    /**
     * Metodo que muestra una ventana emergente con un mensaje de error
     *
     * @param mensaje el texto del error
     */
    private void mostrarAlertErrorValidacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de validacion");
        alert.setHeaderText("No se ha podido completar el registro");
        alert.setContentText(mensaje);

        alert.showAndWait();
    }

    /**
     * Muestra una confirmación para acceder a la cartelera tras registrarse
     */
    private void mostrarAlertConfirm() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Desea acceder a la visualizacion de peliculas?");
        alert.setContentText("Usuario: " + txtNombre.getText());

        ButtonType btnSi = new ButtonType("Si");
        ButtonType btnNo = new ButtonType("No");

        alert.getButtonTypes().setAll(btnSi, btnNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == btnSi) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/pelicula_view.fxml"));
                    Parent root = fxmlLoader.load();

                    PeliculaController peliculaController = fxmlLoader.getController();
                    peliculaController.inicioUsuario(txtNombre.getText());
                    String dni=txtDni.getText();
                    PeliculaRepository repository = new JsonManager(dni);
                    PeliculaService service = new PeliculaService(repository);
                    peliculaController.setPeliculaService(service);

                    txtNombre.getScene().setRoot(root);

                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlertErrorValidacion("Error al cambiar a la vista de peliculas");
                }
            }
        });
    }
}